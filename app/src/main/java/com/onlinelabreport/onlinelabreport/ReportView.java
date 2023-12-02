package com.onlinelabreport.onlinelabreport;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportView extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    EditText pdfUrl;
    Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);

        pdfUrl = findViewById(R.id.pdfUrlText);
        downloadBtn = findViewById(R.id.btnDownload);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    } else {
                        startDownloading();
                    }
                } else {
                    startDownloading();
                }
            }
        });
    }

    private void startDownloading() {
        String url = pdfUrl.getText().toString().trim();

        if (url.isEmpty()) {
            Toast.makeText(this, "URL is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isGoogleDriveLink(url)) {
            // Handle Google Drive link to obtain direct download link
            url = getDirectDownloadLink(url);
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        // Set other properties for the download request
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis());

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    private boolean isGoogleDriveLink(String url) {
        // Check if the URL is a Google Drive link
        return url.contains("drive.google.com");
    }

    private String getDirectDownloadLink(String driveLink) {
        // Extract the file ID from the Google Drive link
        String fileId = extractFileId(driveLink);

        // Construct the direct download link
        return "https://drive.google.com/uc?id=" + fileId;
    }

    private String extractFileId(String driveLink) {
        String fileId = "";
        String[] patterns = {
                "/file/d/(.*?)/",
                "/open\\?id=(.*?)&",
                "/uc\\?id=(.*?)&"
        };

        for (String pattern : patterns) {
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(driveLink);
            if (m.find()) {
                fileId = m.group(1);
                break;
            }
        }

        return fileId;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    startDownloading();
                } else {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
