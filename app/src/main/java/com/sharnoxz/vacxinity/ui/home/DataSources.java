package com.sharnoxz.vacxinity.ui.home;

import com.sharnoxz.vacxinity.R;

import java.util.ArrayList;

public class DataSources{
    public ArrayList<VerticalData> loadVerticalData(){
        ArrayList<VerticalData> data = new ArrayList<VerticalData>();
        VerticalData
                verticalData = new VerticalData(loadHorizontalData2(),"Facilities");
        data.add(verticalData);
        verticalData = new VerticalData(loadHorizontalData1(),"Precautions");
        data.add(verticalData);
        return data;
    }

    private ArrayList<HorizontalData> loadHorizontalData1() {
        ArrayList<HorizontalData> data = new ArrayList<HorizontalData>();
        HorizontalData
                horizontalData = new HorizontalData(R.drawable.stay_informed,"Stay Informed", "https://www.who.int/news?healthtopics=b6bd35a3-cf4f-4851-8e80-85cb0068335b");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.call_a_doctor,"Call Doctor","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.no_handshake,"No handshake","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.sneezing_in_elbow,"Sneezing in elbow","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.social_distancing,"Social Distancing","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.washing_hands,"Washing Hands","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.throw_used_tissue,"Throw used tissue","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.avoid_touching_face,"Avoid toughing Face","https://www.google.com");
        data.add(horizontalData);
        return data;
    }

    private ArrayList<HorizontalData> loadHorizontalData2() {
        ArrayList<HorizontalData> data = new ArrayList<HorizontalData>();
        HorizontalData
                horizontalData = new HorizontalData(R.drawable.quarantine,"Quarantine","https://www.cdc.gov/coronavirus/2019-ncov/faq.html");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.coronavirus,"Corona Virus","https://www.avert.org/coronavirus/faqs");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.difficulty_breathing,"Breathing Difficulty","https://www.healthline.com/health/coronavirus-shortness-of-breath");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.fever,"Fever","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.pneumonia,"Pneumonia","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.dry_cough,"Dry Cough","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.runny_nose,"Runny Nose","https://www.google.com");
        data.add(horizontalData);
        horizontalData = new HorizontalData(R.drawable.sore_throat,"Sore throat","https://www.google.com");
        data.add(horizontalData);
        return data;
    }

}
