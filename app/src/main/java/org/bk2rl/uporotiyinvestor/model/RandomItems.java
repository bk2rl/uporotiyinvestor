package org.bk2rl.uporotiyinvestor.model;

import android.content.Context;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItems {

    public static final int MAX_ITEMS_VALUE = 6;
    private static final int PRODUCTS_NAMES_INDEX = 0;
    private static final int PRODUCTS_IMAGE_FILE_INDEX = PRODUCTS_NAMES_INDEX + 1;
    private static final int FEATURES_NAMES_INDEX = 2;
    private static final int FEATURES_IMAGE_FILE_INDEX = FEATURES_NAMES_INDEX + 1;
    private static final int PROBLEMS_NAMES_INDEX = 4;
    private static final int PROBLEMS_IMAGE_FILE_INDEX = PROBLEMS_NAMES_INDEX + 1;

    private static List<Item> products;
    private static List<Item> features;
    private static List<Item> problems;

    private static final Random random = new Random();

    private RandomItems() {

    }

    public static void initialize(Context context) {
        try {
            TsvParserSettings settings = new TsvParserSettings();
            settings.getFormat().setLineSeparator("\r\n");
//            settings.selectFields("products","features","problems");
            TsvParser parser = new TsvParser(settings);
            List<String[]> parsedRows = parser.parseAll(context.getAssets().open("data.tsv"));

            products = new ArrayList<>();
            for (int i = 0; i < parsedRows.get(PRODUCTS_NAMES_INDEX).length; i++) {
                products.add(new Product().setItem(parsedRows.get(PRODUCTS_NAMES_INDEX)[i]).setImgSrc(parsedRows.get(PRODUCTS_IMAGE_FILE_INDEX)[i]));
            }

            features = new ArrayList<>();
            for (int i = 0; i < parsedRows.get(FEATURES_NAMES_INDEX).length; i++) {
                features.add(new Feature().setItem(parsedRows.get(FEATURES_NAMES_INDEX)[i]).setImgSrc(parsedRows.get(FEATURES_IMAGE_FILE_INDEX)[i]));
            }

            problems = new ArrayList<>();
            for (int i = 0; i < parsedRows.get(PROBLEMS_NAMES_INDEX).length; i++) {
                problems.add(new Problem().setItem(parsedRows.get(PROBLEMS_NAMES_INDEX)[i]).setImgSrc(parsedRows.get(PROBLEMS_IMAGE_FILE_INDEX)[i]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> getRandomProducts(){
        ArrayList<Item> products = new ArrayList<>();
        Item randomProduct;
        for (int i = 0; i < MAX_ITEMS_VALUE; i++){
            do {
                randomProduct = RandomItems.products.get(random.nextInt(RandomItems.products.size()));
            } while (products.contains(randomProduct));
            products.add(randomProduct);
        }
        return products;
    }

    public static ArrayList<Item> getRandomProblems(){
        ArrayList<Item> products = new ArrayList<>();
        Item randomProduct;
        for (int i = 0; i < MAX_ITEMS_VALUE; i++){
            do {
                randomProduct = RandomItems.problems.get(random.nextInt(RandomItems.problems.size()));
            } while (products.contains(randomProduct));
            products.add(randomProduct);
        }
        return products;
    }

    public static ArrayList<Item> getRandomFeatures(){
        ArrayList<Item> products = new ArrayList<>();
        Item randomFeature;
        for (int i = 0; i < MAX_ITEMS_VALUE; i++){
            do {
                randomFeature = RandomItems.features.get(random.nextInt(RandomItems.features.size()));
            } while (products.contains(randomFeature));
            products.add(randomFeature);
        }
        return products;
    }

}
