package com.example.cardiotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * The HealthDataAdapter is a custom ArrayAdapter used to populate a ListView with HealthData objects.
 * It handles the creation of the list item views and displays the health data information in the appropriate views.
 */
public class HealthDataAdapter extends ArrayAdapter<HealthData> {
    /**
     * Constructs a HealthDataAdapter with the given context and data list.
     *
     * @param context   the context of the adapter
     * @param dataList  the list of HealthData objects to be displayed
     */
    public HealthDataAdapter(Context context, List<HealthData> dataList) {
        super(context, 0, dataList);
    }
    /**
     * Gets a View that displays the data at the specified position in the data list.
     *
     * @param position       the position of the item within the adapter's data set
     * @param convertView    the old view to reuse, if possible
     * @param parent         the parent ViewGroup containing the convertView
     * @return the View corresponding to the data at the specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item_view, parent, false);
        }

        HealthData healthData = getItem(position);

        TextView textDate = convertView.findViewById(R.id.textDate);
        TextView textTime = convertView.findViewById(R.id.textTime);
        TextView textDiastolicPressure = convertView.findViewById(R.id.textDiastolicPressure);
        TextView textSystolicPressure = convertView.findViewById(R.id.textSystolicPressure);
        TextView textHeartRate = convertView.findViewById(R.id.textHeartRate);
        TextView textComment = convertView.findViewById(R.id.textComment);
        ImageView statusIndicator = convertView.findViewById(R.id.statusIndicator);

        textDate.setText(healthData.getDate());
        textDiastolicPressure.setText("Diastolic Pressure: " + healthData.getDiastolicPressure());
        textSystolicPressure.setText("Systolic Pressure: " + healthData.getSystolicPressure());
        textHeartRate.setText("Heart Rate: " + healthData.getHeartRate());
        textComment.setText("Comment: " + healthData.getComment());

        try {
            int heartRate = Integer.parseInt(healthData.getHeartRate().split(" ")[0]);
            int diastolicPressure = Integer.parseInt(healthData.getDiastolicPressure().split(" ")[0]);
            int systolicPressure = Integer.parseInt(healthData.getSystolicPressure().split(" ")[0]);

            if (heartRate >= 60 && heartRate <= 100 && diastolicPressure >= 60 && diastolicPressure <= 80 && systolicPressure >= 90 && systolicPressure <= 120) {
                statusIndicator.setImageResource(R.drawable.green_circle); // Normal status
            } else {
                statusIndicator.setImageResource(R.drawable.red_circle); // Abnormal status
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for heart rate, diastolic pressure, or systolic pressure
            statusIndicator.setImageResource(R.drawable.red_circle); // Abnormal status
        }

        // Format the time
        String formattedTime = formatTime(healthData.getTime());
        textTime.setText(formattedTime);

        return convertView;
    }
    /**
     * Formats the given time string into the desired format.
     *
     * @param time  the time string to be formatted
     * @return the formatted time string
     */
    private String formatTime(String time) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
            Date date = inputFormat.parse(time);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
