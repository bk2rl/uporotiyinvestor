package org.bk2rl.uporotiyinvestor.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.bk2rl.uporotiyinvestor.R;
import org.bk2rl.uporotiyinvestor.model.Feature;
import org.bk2rl.uporotiyinvestor.model.Item;
import org.bk2rl.uporotiyinvestor.model.Problem;
import org.bk2rl.uporotiyinvestor.model.Product;
import org.bk2rl.uporotiyinvestor.model.RandomItems;

import java.util.HashMap;

public class UporotoeActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener,
ResultFragment.OnFragmentInteractionListener, GreetingsFragment.OnFragmentInteractionListener{

    public static final int PRODUCT_INDEX = 0;
    public static final int FEATURE_INDEX = 2;
    public static final int PROBLEM_INDEX = 1;
    private static final String PRODUCT_FRAGMENT = "product-fragment";
    private static final String FEATURE_FRAGMENT = "feature-fragment";
    private static final String PROBLEM_FRAGMENT = "problem-fragment";
    private HashMap<String,Fragment> fragments = new HashMap<>();
    private String complexItem[] = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments.put(PRODUCT_FRAGMENT, new ItemFragment.ItemFragmentBuilder()
                .setRandomItems(RandomItems.getRandomProducts())
                .setColumnCount(2)
                .setDrawableResource(R.drawable.product2)
                .setFirstColor(getResources().getColor(R.color.product))
                .setSecondColor(getResources().getColor(R.color.productDark))
                .setText(getResources().getString(R.string.second_stage_product)).build());

        fragments.put(FEATURE_FRAGMENT,new ItemFragment.ItemFragmentBuilder()
                .setRandomItems(RandomItems.getRandomFeatures())
                .setColumnCount(2)
                .setDrawableResource(R.drawable.feature3)
                .setFirstColor(getResources().getColor(R.color.feature))
                .setSecondColor(getResources().getColor(R.color.featureDark))
                .setText(getResources().getString(R.string.third_stage_feature)).build());

        fragments.put(PROBLEM_FRAGMENT,new ItemFragment.ItemFragmentBuilder()
                .setRandomItems(RandomItems.getRandomProblems())
                .setColumnCount(2)
                .setDrawableResource(R.drawable.problem1)
                .setFirstColor(getResources().getColor(R.color.problem))
                .setSecondColor(getResources().getColor(R.color.problemDark))
                .setText(getResources().getString(R.string.first_stage_problem)).build());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, GreetingsFragment.newInstance()).commit();
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        RandomItems randomProduct = RandomItems.generate();
//        ((TextView) findViewById(R.id.product)).setText(randomProduct.getProduct());
//        ((TextView) findViewById(R.id.possibilities)).setText(randomProduct.getPossibility());
//        ((TextView) findViewById(R.id.problems)).setText(randomProduct.getProblem());
    }

    @Override
    public void onListFragmentInteraction(Item item) {
        if (item instanceof Product){
            complexItem[PRODUCT_INDEX] = item.getItem();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.get(FEATURE_FRAGMENT)).addToBackStack(null).commit();
        } else if (item instanceof Problem){
            complexItem[PROBLEM_INDEX] = item.getItem();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.get(PRODUCT_FRAGMENT)).addToBackStack(null).commit();
        } else if (item instanceof Feature) {
            complexItem[FEATURE_INDEX] = item.getItem();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ResultFragment.newInstance(complexItem)).addToBackStack(null).commit();

        }
    }

    @Override
    public void onFragmentInteraction(String string) {
        switch (string){
            case "switch-to-start":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.get(PROBLEM_FRAGMENT)).addToBackStack(null).commit();
                break;
        }
    }

}
