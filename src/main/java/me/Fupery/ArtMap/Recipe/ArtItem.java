package me.Fupery.ArtMap.Recipe;

import java.awt.*;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import me.Fupery.ArtMap.ArtMap;
import me.Fupery.ArtMap.api.Config.Lang;

import static java.awt.Color.*;
import static org.bukkit.ChatColor.*;

public class ArtItem {

    private static final String ARTWORK_TAG = "§7État §7»§f Fini";
    private static final String UNFINISHED_TAG = "§7État §7»§f Non fini";
    private static final String COPY_TAG = "§7État §7»§f Copiée";
    public static final String CANVAS_KEY = "§7État §7»§f Feuille NFT";
    public static final String EASEL_KEY = "§7État §7»§f Chevalet NFT";
    public static final String KIT_KEY = ChatColor.DARK_GRAY + "[KIT NFT]";
    public static final String PREVIEW_KEY = "§7État §7»§f Aperçu";
    public static final String PAINT_BRUSH = "§7État §7»§f Pinceau";
    
    private ArtItem() {
        //hide consstructor
    }

    /**
     * Determine wether the provided ItemStack is an Artwork
     * @param itemStack The ItemStack to check.
     * @return  True if the ItemStack is an original Artwork false otherwise.
     */
    public static boolean isArtwork(ItemStack itemStack) {
        if (itemStack != null
                && itemStack.getType() == Material.FILLED_MAP
		        && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore() && (itemMeta.getLore().get(0).contains(ARTWORK_TAG) || 
                itemMeta.getLore().get(0).contains(UNFINISHED_TAG) ||
                itemMeta.getLore().get(0).contains(COPY_TAG) )) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine wether the provided ItemStack is an Original Artwork.
     * @param itemStack The ItemStack to check.
     * @return  True if the ItemStack is an original Artwork false otherwise.
     */
    public static boolean isOriginalArtwork(ItemStack itemStack) {
        if (itemStack != null
                && itemStack.getType() == Material.FILLED_MAP
		        && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore() && itemMeta.getLore().get(0).contains(ARTWORK_TAG)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the provided ItemStack is an Unfinished Artwork.
     * @param itemStack The ItemStack to check.
     * @return  True if the ItemStack is an Unfinished Artwork false otherwise.
     */
    public static boolean isUnfinishedArtwork(ItemStack itemStack) {
        if (itemStack != null
                && itemStack.getType() == Material.FILLED_MAP
		        && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore() && itemMeta.getLore().get(0).contains(UNFINISHED_TAG)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether the provided ItemStack is an Copy Artwork.
     * @param itemStack The ItemStack to check.
     * @return  True if the ItemStack is an Copy Artwork false otherwise.
     */
    public static boolean isCopyArtwork(ItemStack itemStack) {
        if (itemStack != null
                && itemStack.getType() == Material.FILLED_MAP
		        && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore() && itemMeta.getLore().get(0).contains(COPY_TAG)) {
                return true;
            }
        }
        return false;
    }

	//private static final char BULLET_POINT = '\u2022';

    static class CraftableItem extends CustomItem {

        public CraftableItem(String itemName, Material material, String uniqueKey) {
            super(material, KIT_KEY, uniqueKey);
            try {
                recipe(ArtMap.instance().getRecipeLoader().getRecipe(itemName.toUpperCase()));
            } catch (RecipeLoader.InvalidRecipeException e) {
                ArtMap.instance().getLogger().log(Level.SEVERE, "Failure!", e);
            }
        }
    }

    public static class ArtworkItem extends CustomItem {
        /**
         * Creates an original Artwork item.
         * @param id - The Map ID of the artwork.
         * @param title - The title of the artwork.
         * @param artistName - The name of the artist.
         * @param date - The date the item was created.
         */
        public ArtworkItem(int id, String title, String artistName, String date) {
			super(new ItemStack(Material.FILLED_MAP), ARTWORK_TAG);
			MapMeta meta = (MapMeta) this.stack.get().getItemMeta();
			meta.setMapView(ArtMap.getMap(id));
			this.stack.get().setItemMeta(meta);
            name(title);
            String artist = "§f" + String.format(Lang.RECIPE_ARTWORK_ARTIST.get(), ("§e" + artistName));
            tooltip(artist, "§7Date de création §7» " + "§f" + date);
        }
    }

    public static class InProgressArtworkItem extends CustomItem {
        /**
         * Creates an in progress artwork item.
         * @param id - The map id of the inprogress artwork.
         */
        public InProgressArtworkItem(int id, String artistName) {
			super(new ItemStack(Material.FILLED_MAP), UNFINISHED_TAG);
			MapMeta meta = (MapMeta) this.stack.get().getItemMeta();
			meta.setMapView(ArtMap.getMap(id));
			this.stack.get().setItemMeta(meta);
            name("NFT non Fini");
            String artist = "§f" + String.format(Lang.RECIPE_ARTWORK_ARTIST.get(), ("§e" + artistName));
            tooltip(artist);
        }
    }

    public static class CopyArtworkItem extends CustomItem {
        /**
         * Creates a copy artowrk item.
         * @param id
         */
        public CopyArtworkItem(int id, String title, String artistName, String date) {
			super(new ItemStack(Material.FILLED_MAP), COPY_TAG);
			MapMeta meta = (MapMeta) this.stack.get().getItemMeta();
			meta.setMapView(ArtMap.getMap(id));
			this.stack.get().setItemMeta(meta);
            name(title);
            String artist = "§f" + String.format(Lang.RECIPE_ARTWORK_ARTIST.get(), ("§e" + artistName));
            tooltip(artist, "§7Date de création §7» " + "§f" + date);
        }
    }

    public static class KitItem extends CustomItem {
        KitItem(Material material, String name) {
            super(material, KIT_KEY, name);
        }
    }
}
