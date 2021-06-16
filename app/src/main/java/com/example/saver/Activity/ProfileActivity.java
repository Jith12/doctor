package com.example.saver.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.saver.Helper.FileUtils;
import com.example.saver.Helper.ImageURL;
import com.example.saver.Model.City;
import com.example.saver.Model.Country;
import com.example.saver.Model.Place;
import com.example.saver.Model.State;
import com.example.saver.Others.Loader;
import com.example.saver.Others.Snack;
import com.example.saver.R;
import com.example.saver.Response.LocationResponse;
import com.example.saver.Response.LoginResponse;
import com.example.saver.Response.PlaceResponse;
import com.example.saver.Response.SaveProfile;
import com.example.saver.Response.ViewResponse;
import com.example.saver.Retrofit.ErrorUtils;
import com.example.saver.Retrofit.RetrofitAPI;
import com.example.saver.Retrofit.RetrofitBASE;
import com.example.saver.Retrofit.RetrofitERROR;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.ornach.nobobutton.NoboButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;
import thebat.lib.validutil.ValidUtils;

public class ProfileActivity extends AppCompatActivity {

    private TextFieldBoxes TxtdrName, TxtdrEmailid, TxtdrMobileno, TxtdrLandlineno, TxtdrAddress1, TxtdrAddress2,
                           TxtdrZipcode, TxtdrExperience, TxtdrFees;
    private ExtendedEditText drName, drEmailid, drMobileno, drLandlineno, drAddress1, drAddress2,
                             drZipcode, drExperience, drFees;
    private LinearLayout linearLoc, linearCountry, linearState, linearCity, linearImage;
    private AppCompatTextView place;
    AppCompatTextView country, state, city;
    private AppCompatImageView More, More1, More2, More3;
    private NoboButton btnCancel, btnSubmit;
    private LinearLayout imageLayout;
    private AppCompatImageView drImage, ivImageTrue, ivImageFalse;
    private ArrayList<MediaFile> imageFiles = new ArrayList<>();
    private static final int REQUEST_IMAGE_STORAGE = 1;
    private static final int REQUEST_IMAGE = 2;
    private List<Country> countries = new ArrayList<>();
    private List<State> states = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<Place> places = new ArrayList<>();
    private SpinnerDialog spPriCountry, spPriState, spPriCity, spPriPlaces;
    private ArrayList<String> countryPriList = new ArrayList<>();
    private ArrayList<String> statePriList = new ArrayList<>();
    private ArrayList<String> cityPriList = new ArrayList<>();
    private ArrayList<String> placePriList = new ArrayList<>();
    private ArrayMap<Integer, String> countryPriMap = new ArrayMap<>();
    private ArrayMap<Integer, String> statePriMap = new ArrayMap<>();
    private ArrayMap<Integer, String> cityPriMaps = new ArrayMap<>();
    private ArrayMap<Integer, String> placePriMaps = new ArrayMap<>();
    String countryid, stateid, cityid, placeid;
    private Loader loader;
    private Snack snack;
    private String loginid;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{3,6}$", Pattern.CASE_INSENSITIVE);
    String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loader = new Loader(this);
        snack = new Snack(this);

        loginid = Prefs.getString("e_id", null);

        TxtdrName = findViewById(R.id.drname_txt);
        drName = findViewById(R.id.dr_name);
        TxtdrEmailid = findViewById(R.id.dremailid_txt);
        drEmailid = findViewById(R.id.dr_emailid);
        TxtdrMobileno = findViewById(R.id.drmobile_txt);
        drMobileno = findViewById(R.id.dr_mobileno);
        TxtdrLandlineno = findViewById(R.id.drlandline_txt);
        drLandlineno = findViewById(R.id.dr_landlineno);
        TxtdrAddress1 = findViewById(R.id.draddress1_txt);
        drAddress1 = findViewById(R.id.dr_address1);
        TxtdrAddress2 = findViewById(R.id.draddress2_txt);
        drAddress2 = findViewById(R.id.dr_address2);
        TxtdrZipcode = findViewById(R.id.drzipcode_txt);
        drZipcode = findViewById(R.id.dr_zipcode);
        TxtdrExperience = findViewById(R.id.drexperience_txt);
        drExperience = findViewById(R.id.dr_experience);
        TxtdrFees = findViewById(R.id.drdoctorfee_txt);
        drFees = findViewById(R.id.dr_doctorfee);
        linearLoc = findViewById(R.id.sell_det_loc_layout);
        linearCountry = findViewById(R.id.sell_det_cnty_layout);
        linearState = findViewById(R.id.sell_det_state_layout);
        linearCity = findViewById(R.id.sell_det_city_layout);
        place = findViewById(R.id.sell_det_location);
        country = findViewById(R.id.sell_det_cnty);
        state = findViewById(R.id.sell_det_state);
        city = findViewById(R.id.sell_det_city);
        btnCancel = findViewById(R.id.cancel_btn);
        btnSubmit = findViewById(R.id.submit_btn);
        linearImage = findViewById(R.id.linear_image);
        drImage = findViewById(R.id.dr_image);
        ivImageTrue = findViewById(R.id.sell_profile_upload_true);
        ivImageFalse = findViewById(R.id.sell_profile_upload_false);

        if (ValidUtils.isNetworkAvailable(this)) {
            places();
            viewprofile();
        } else {
            snack.timeout("No Network Available");
        }

        spPriCountry = new SpinnerDialog(this, countryPriList,"Country","Close");
        spPriCountry.setTitleColor(getResources().getColor(R.color.purple_500));
        spPriCountry.setTitleColor(getResources().getColor(R.color.purple_500));
        spPriCountry.setItemDividerColor(getResources().getColor(R.color.black));
        spPriCountry.setCloseColor(getResources().getColor(R.color.black));

        spPriState = new SpinnerDialog(this, new ArrayList<>(),"State","Close");
        spPriState.setTitleColor(getResources().getColor(R.color.purple_500));
        spPriState.setItemDividerColor(getResources().getColor(R.color.black));
        spPriState.setCloseColor(getResources().getColor(R.color.black));

        spPriCity = new SpinnerDialog(this, new ArrayList<>(),"City","Close");
        spPriCity.setTitleColor(getResources().getColor(R.color.purple_500));
        spPriCity.setItemDividerColor(getResources().getColor(R.color.black));
        spPriCity.setCloseColor(getResources().getColor(R.color.black));

        country.setOnClickListener(v -> {
            spPriCountry.showSpinerDialog();
            spPriCountry.setCancellable(false);
            spPriCountry.bindOnSpinerListener((item, position) -> {
                state.setText("");
                city.setText("");
                state.setEnabled(true);
                country.setText(item);
                countryid = countryPriMap.get(position);
                if (!states.isEmpty()){
                    statePriList.clear();statePriMap.clear();
                    for (int i=0; i< states.size(); i++){
                        if (states.get(i).getScountryid().equals(countryPriMap.get(position))){
                            statePriList.add(states.get(i).getStatename());
                            statePriMap.put(i, states.get(i).getStateid());
                        }
                    }
                    spPriState = new SpinnerDialog(this, statePriList,"State","Close");
                    spPriState.setTitleColor(getResources().getColor(R.color.purple_500));
                    spPriState.setItemDividerColor(getResources().getColor(R.color.black));
                    spPriState.setCloseColor(getResources().getColor(R.color.black));
                }
            });
        });

        state.setOnClickListener(v -> {
            spPriState.showSpinerDialog();
            spPriState.setCancellable(false);
            spPriState.bindOnSpinerListener((item, position) -> {
                city.setText("");
                city.setEnabled(true);
                state.setText(item);
                stateid = statePriMap.get(position);
                if (!cities.isEmpty()){
                    cityPriList.clear();cityPriMaps.clear();
                    int j=0;
                    for (int i=0; i<cities.size(); i++){
                        if (cities.get(i).getCstateid().equals(statePriMap.get(position))){
                            cityPriList.add(cities.get(i).getCityname());
                            cityPriMaps.put(j, cities.get(i).getCityid());
                            j++;
                        }
                    }
                    spPriCity = new SpinnerDialog(this, cityPriList,"City","Close");
                    spPriCity.setTitleColor(getResources().getColor(R.color.purple_500));
                    spPriCity.setItemDividerColor(getResources().getColor(R.color.black));
                    spPriCity.setCloseColor(getResources().getColor(R.color.black));
                }
            });
        });

        city.setOnClickListener(v->{
            spPriCity.showSpinerDialog();
            spPriCity.setCancellable(false);
            spPriCity.bindOnSpinerListener((item, position) -> {
                city.setText(item);
                cityid = cityPriMaps.get(position);

            });
        });

        place.setOnClickListener(v->{
            spPriPlaces.showSpinerDialog();
            spPriPlaces.setCancellable(false);
            spPriPlaces.bindOnSpinerListener((item, position) -> {
                place.setText(item);
                placeid = placePriMaps.get(position);

            });
        });

        btnSubmit.setOnClickListener(v -> {
            if(Validate())
            {
                String drname = drName.getText().toString();
                String dremail = drEmailid.getText().toString();
                String drmobile = drMobileno.getText().toString();
                String drlandline = drLandlineno.getText().toString();
                String location = placeid;
                String draddress1 = drAddress1.getText().toString();
                String draddress2 = drAddress2.getText().toString();
                String country = countryid;
                String state = stateid;
                String city = cityid;
                String pincode = drZipcode.getText().toString();
                String experience = drExperience.getText().toString();
                String fees = drFees.getText().toString();

                Save(drname, dremail, drmobile, drlandline, location, draddress1, draddress2, country, state, city, pincode, experience, fees);
            }
        });

        linearImage.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // reuqest for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_STORAGE);
            } else {
                Intent intent = new Intent(this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(false)
                        .setMaxSelection(1)
                        .setSkipZeroSizeFiles(true)
                        .setImageSize(300)
                        .setShowAudios(false)
                        .setShowFiles(false)
                        .setSingleChoiceMode(true)
                        .setSingleClickSelection(true)
                        .setSelectedMediaFiles(imageFiles)
                        .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/")
                        .build());
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        btnCancel.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            Animatoo.animateFade(ProfileActivity.this);
            finish();
        });
    }

    private void Save(String drname, String dremail, String drmobile, String drlandline, String location, String draddress1,
                      String draddress2, String country, String state, String city, String pincode,
                      String experience, String fees) {

        MediaType mediaType = MediaType.parse("application/json");
        MultipartBody.Part[] profileParts = new MultipartBody.Part[imageFiles.size()];
        for (int i = 0; i < imageFiles.size(); i++) {
            File file = new File(Objects.requireNonNull(FileUtils.getPath(imageFiles.get(i).getUri(), ProfileActivity.this)));
            RequestBody fileBody = RequestBody.create(mediaType, file);
            profileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "profile[%d]", i), file.getName(), fileBody);
        }

        RequestBody loginiddr =  MultipartBody.create(MultipartBody.FORM, loginid);
        RequestBody namedr =  MultipartBody.create(MultipartBody.FORM, drname);
        RequestBody emailiddr =  MultipartBody.create(MultipartBody.FORM, dremail);
        RequestBody mobildr =  MultipartBody.create(MultipartBody.FORM, drmobile);
        RequestBody landlinedr =  MultipartBody.create(MultipartBody.FORM, drlandline);
        RequestBody locationdr =  MultipartBody.create(MultipartBody.FORM, location);
        RequestBody address1dr =  MultipartBody.create(MultipartBody.FORM, draddress1);
        RequestBody address2dr =  MultipartBody.create(MultipartBody.FORM, draddress2);
        RequestBody countrydr =  MultipartBody.create(MultipartBody.FORM, country);
        RequestBody statedr =  MultipartBody.create(MultipartBody.FORM, state);
        RequestBody citydr =  MultipartBody.create(MultipartBody.FORM, city);
        RequestBody pincodedr =  MultipartBody.create(MultipartBody.FORM, pincode);
        RequestBody experiencedr =  MultipartBody.create(MultipartBody.FORM, experience);
        RequestBody feesdr =  MultipartBody.create(MultipartBody.FORM, fees);

        RetrofitAPI api = RetrofitBASE.getRetrofit(this).create(RetrofitAPI.class);
        Call<SaveProfile> call = api.saveprofile(loginiddr, namedr, emailiddr, mobildr, landlinedr, locationdr,
                address1dr, address2dr, countrydr, statedr, citydr, pincodedr, experiencedr, feesdr, profileParts);
        call.enqueue(new Callback<SaveProfile>() {
            @Override
            public void onResponse(@NotNull Call<SaveProfile> call, @NotNull Response<SaveProfile> response) {
                try {
                    if (response.isSuccessful()){
                        SaveProfile data = response.body();
                        loader.dismiss("");
                        if (data != null){
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status){

                                snack.success(message);

                                SaveProfile.Data result = data.getData();
                                Prefs.putBoolean("logged_in", true);
                                Prefs.putString("e_id", result.getId());
                                Prefs.putString("e_name", result.getName());

                                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                                Animatoo.animateFade(ProfileActivity.this);
                                finish();

                            }else {
                                snack.failure(message);
                            }
                        }
                    }else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.timeout(errorUtils.message());
                        Log.e("RegisterError", Objects.requireNonNull(errorUtils.message()));
                    }
                }catch (Exception e){
                    loader.dismiss("");
                    Log.e("RegisterException", Objects.requireNonNull(e.getMessage()));
                }
            }
            @Override
            public void onFailure(@NotNull Call<SaveProfile> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> Save(drname, dremail, drmobile, drlandline, location, draddress1,
                            draddress2, country, state, city, pincode,
                            experience, fees), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.timeout("Unknown Host, Check your URL");
                }
                Log.e("RegisterFailure", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            List<MediaFile> mediaFiles = data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            if(Objects.requireNonNull(mediaFiles).size() > 0) {
                setProfileFiles(mediaFiles);
                ivImageFalse.setVisibility(View.GONE);
                ivImageTrue.setVisibility(View.VISIBLE);
            }else {
                snack.timeout("No Image has been Selected");
                ivImageFalse.setVisibility(View.VISIBLE);
                ivImageTrue.setVisibility(View.GONE);
            }
        }
    }

    private void setProfileFiles(List<MediaFile> mediaFiles) {
        this.imageFiles.clear();
        this.imageFiles.addAll(mediaFiles);
        Uri imageUri = mediaFiles.get(0).getUri();
        drImage.setImageURI(imageUri);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_IMAGE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, FilePickerActivity.class);
                    intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .enableImageCapture(false)
                            .setMaxSelection(1)
                            .setSkipZeroSizeFiles(true)
                            .setImageSize(300)
                            .setShowAudios(false)
                            .setShowFiles(false)
                            .setSingleChoiceMode(true)
                            .setSingleClickSelection(true)
                            .setSelectedMediaFiles(imageFiles)
                            .setRootPath(Environment.getExternalStorageDirectory().getPath() + "/")
                            .build());
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else {
                    snack.timeout("Storage Permission Denied");
                }
                break;
            }
        }
    }

    private boolean Validate() {

        if(drName.getText().toString().isEmpty())
        {
            TxtdrName.setError("Doctor Name Filed can't be empty!..", true);
            return false;
        }
        else if(drEmailid.getText().toString().isEmpty())
        {
            TxtdrEmailid.setError("Email Id Filed can't be empty!..", true);
            return false;
        }
        else if(!drEmailid.getText().toString().isEmpty()
                && !EMAIL_PATTERN.matcher(drEmailid.getText().toString()).matches())
        {
            TxtdrEmailid.setError("Invalidate Email Address!..", true);
            return false;
        }
        else if(drMobileno.getText().toString().isEmpty() && drMobileno.getText().toString().isEmpty())
        {
            TxtdrMobileno.setError("Mobile Number field can't be empty!..", true);
            return false;
        }
        else if(!drMobileno.getText().toString().isEmpty()
                && !drMobileno.getText().toString().matches(regexStr))
        {
            TxtdrEmailid.setError("Invalidate Mobile Number!..", true);
            return false;
        }
        else if(place.getText().toString().isEmpty())
        {
            Toast.makeText(ProfileActivity.this, "Location field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(drAddress1.getText().toString().isEmpty())
        {
            TxtdrAddress1.setError("Address Filed can't be empty!..", true);
            return false;
        }
        else if(country.getText().toString().isEmpty())
        {
            Toast.makeText(ProfileActivity.this, "Country field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(state.getText().toString().isEmpty())
        {
            Toast.makeText(ProfileActivity.this, "State field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(city.getText().toString().isEmpty())
        {
            Toast.makeText(ProfileActivity.this, "City field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(drZipcode.getText().toString().isEmpty())
        {
            TxtdrZipcode.setError("Pincode field can't be empty!..", true);
            return false;
        }
        else if(drExperience.getText().toString().isEmpty())
        {
            TxtdrExperience.setError("Experience field can't be empty!..", true);
            return false;
        }
        else if(drFees.getText().toString().isEmpty())
        {
            TxtdrFees.setError("Doctor Fees field can't be empty!..", true);
            return false;
        }
        else
        {
            return true;
        }
    }

    private void viewprofile() {

       // loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(this).create(RetrofitAPI.class);
        Call<ViewResponse> call = api.viewprofile(loginid);

        call.enqueue(new Callback<ViewResponse>() {
            @Override
            public void onResponse(@NotNull Call<ViewResponse> call, @NotNull Response<ViewResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        ViewResponse data = response.body();
                        //loader.dismiss("");
                        if (data != null) {
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status) {


                                ViewResponse.Data results = data.getData();

                                drName.setText(results.getDoctorName());
                                drEmailid.setText(results.getEmail());
                                drMobileno.setText(results.getMobile());
                                drLandlineno.setText(results.getLandline());
                                drAddress1.setText(results.getAddress1());
                                drAddress2.setText(results.getAddress2());
                                drZipcode.setText(results.getZipcode());
                                drExperience.setText(results.getExperience());
                                drFees.setText(results.getDoctorFee());

                                Glide.with(ProfileActivity.this)
                                        .load(ImageURL.USER_IMG_PATH+results.getProfile())
                                        .into(drImage);

                                if(results.getCountryId() != null && !results.getCountryName().isEmpty()) {
                                    place.setText(results.getPlaceName());
                                    country.setText(results.getCountryName());
                                    state.setText(results.getStateName());
                                    city.setText(results.getCityName());
                                    countryid = results.getCountryId();
                                    stateid = results.getStateId();
                                    cityid = results.getCityId();
                                    placeid = results.getPlaceId();
                                }
                            }
                        }
                    } else {
                        //loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.timeout(errorUtils.message());
                        Log.e("ProfileError", Objects.requireNonNull(errorUtils.message()));
                    }
                } catch (Exception e) {
                    //loader.dismiss("");
                    Log.e("ProfileException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ViewResponse> call, @NotNull Throwable t) {
                //loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> viewprofile(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("ProfileFailure", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void places() {

        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(this).create(RetrofitAPI.class);
        Call<PlaceResponse> call = api.places();

        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlaceResponse> call, @NotNull Response<PlaceResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        PlaceResponse data = response.body();
                        loader.dismiss("");
                        if (data != null) {
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status) {

                                List<PlaceResponse.Datum> placesResult = data.getData();

                                placePriList.clear();placePriMaps.clear();
                                places.clear();
                                if (!placesResult.isEmpty()){
                                    for (int i=0; i<placesResult.size(); i++){
                                        Place placess = new Place(placesResult.get(i).getId(), placesResult.get(i).getLocation());
                                        places.add(placess);
                                        placePriList.add(placesResult.get(i).getLocation());
                                        placePriMaps.put(i, placesResult.get(i).getId());
                                    }
                                }

                                spPriPlaces = new SpinnerDialog(ProfileActivity.this, placePriList,"Location","Close");
                                spPriPlaces.setTitleColor(getResources().getColor(R.color.primary));
                                spPriPlaces.setTitleColor(getResources().getColor(R.color.primary));
                                spPriPlaces.setItemDividerColor(getResources().getColor(R.color.black));
                                spPriPlaces.setCloseColor(getResources().getColor(R.color.black));

                                locations(); //For loading values country , state, city

                            }
                        }
                    } else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.timeout(errorUtils.message());
                        Log.e("PlaceError", Objects.requireNonNull(errorUtils.message()));
                    }
                } catch (Exception e) {
                    loader.dismiss("");
                    Log.e("PlaceException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<PlaceResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> places(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("PlaceFailure", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void locations() {
        loader.show("");
        RetrofitAPI api = RetrofitBASE.getRetrofit(this).create(RetrofitAPI.class);
        Call<LocationResponse> call = api.locations();

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        LocationResponse data = response.body();
                        loader.dismiss("");
                        if (data != null) {
                            Boolean status = Objects.requireNonNull(data).getStatus();
                            String message = data.getMessage();
                            if (status) {

                                loader.dismiss("");

                                List<LocationResponse.Country> countryResult = data.getCountry();
                                List<LocationResponse.State> stateResult = data.getState();
                                List<LocationResponse.City> cityResult = data.getCity();
                                countryPriList.clear();countryPriMap.clear();
                                countries.clear();states.clear();cities.clear();
                                if (!countryResult.isEmpty()){
                                    for (int i=0; i<countryResult.size(); i++){
                                        Country country = new Country(countryResult.get(i).getCountryId(), countryResult.get(i).getCountryName(),
                                                countryResult.get(i).getStatus());
                                        countries.add(country);
                                        countryPriList.add(countryResult.get(i).getCountryName());
                                        countryPriMap.put(i, countryResult.get(i).getCountryId());
                                    }
                                }
                                if (!stateResult.isEmpty()){
                                    for (int i=0; i<stateResult.size(); i++){
                                        State state = new State(stateResult.get(i).getSstateId(), stateResult.get(i).getSstateName(),
                                                stateResult.get(i).getScountryId(), stateResult.get(i).getSstatus());
                                        states.add(state);
                                    }
                                }
                                if (!cityResult.isEmpty()){
                                    for (int i=0; i<cityResult.size(); i++){
                                        City city = new City(cityResult.get(i).getCcityId(), cityResult.get(i).getCcityName(),
                                                cityResult.get(i).getCstateId());
                                        cities.add(city);
                                    }
                                }

                                spPriCountry = new SpinnerDialog(ProfileActivity.this, countryPriList,"Country","Close");
                                spPriCountry.setTitleColor(getResources().getColor(R.color.primary));
                                spPriCountry.setTitleColor(getResources().getColor(R.color.primary));
                                spPriCountry.setItemDividerColor(getResources().getColor(R.color.black));
                                spPriCountry.setCloseColor(getResources().getColor(R.color.black));

                            }
                        }
                        else
                        {
                            loader.dismiss("");
                        }
                    } else {
                        loader.dismiss("");
                        ErrorUtils errorUtils = RetrofitERROR.parseError(response);
                        snack.timeout(errorUtils.message());
                        Log.e("LocationError", Objects.requireNonNull(errorUtils.message()));
                    }
                } catch (Exception e) {
                    loader.dismiss("");
                    Log.e("LocationException", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                loader.dismiss("");
                if (t instanceof SocketTimeoutException){
                    snack.timeout("Timeout, Retrying Again. Please Wait");
                    new Handler().postDelayed(() -> locations(), 3000);
                }else if (t instanceof UnknownHostException){
                    snack.host("Unknown Host, Check your URL");
                }
                Log.e("LocationFailure", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}