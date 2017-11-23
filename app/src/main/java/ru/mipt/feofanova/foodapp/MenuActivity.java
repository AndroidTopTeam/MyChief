package ru.mipt.feofanova.foodapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements ImageDownloaderTask.IImageResponseListener
{
    TextView mTitleTextView;
    ImageView mMealPhoto;
    TextView mIngredientsTitleTextView;
    TextView mIngredientsList;
    TextView mRecipeTextView;
    TextView mRecipeDescriptionTextView;
    GsonMealObject currentMeal;
    private ImageDownloaderTask imgResponseTask;

    @Override
    public void onResponse(Bitmap img, ImageView currentImage)
    {
        //currentImage.setImageBitmap(img);
        currentImage.setImageBitmap(img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTitleTextView = (TextView)findViewById(R.id.title);
        mMealPhoto = (ImageView)findViewById(R.id.photo);
        mIngredientsTitleTextView = (TextView)findViewById(R.id.ingredients);
        mIngredientsList = (TextView) findViewById(R.id.ingredients_list);
        mRecipeTextView = (TextView)findViewById(R.id.recipe);
        mRecipeDescriptionTextView = (TextView)findViewById(R.id.recipe_description);

        int index = getIntent().getIntExtra("currentMealIndex", 0);
        currentMeal = Singleton.getInstance().getParsedJsonResp().get(index);
        //Log.e("----CURRENTMEAL",currentMeal.getTitle());

        mTitleTextView.setText(currentMeal.getTitle());

        mIngredientsList.setText(currentMeal.getIngredients());
        mRecipeDescriptionTextView.setText(currentMeal.getHref());
        mRecipeDescriptionTextView.setTextColor(Color.BLUE);
        mRecipeDescriptionTextView.setHighlightColor(Color.BLUE);

        imgResponseTask = new ImageDownloaderTask(currentMeal.getThumbnail(), mMealPhoto);
        imgResponseTask.delegate = MenuActivity.this;
        imgResponseTask.execute();

        mRecipeDescriptionTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentMeal.getHref())));
            }
        }
        );
    }
}
