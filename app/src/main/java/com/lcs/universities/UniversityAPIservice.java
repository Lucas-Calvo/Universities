package com.lcs.universities;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface UniversityAPIservice {
    @GET("search?country=Spain")
    Call<List<University>> getUniversities();
}
