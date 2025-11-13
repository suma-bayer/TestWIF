package org.example;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ExternalAccountCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExternalAccountCredentialsExample {

    public static void main(String[] args) {
        // Path to your external account credentials JSON file
        String projectId = "bcs-veg-lake-np";
        String credentialsFilePath = "vegpmcnp.json";

        try (InputStream credentialsStream = new FileInputStream(credentialsFilePath)) {
            // Load the credentials from the JSON stream
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Ensure the loaded credentials are ExternalAccountCredentials
            if (credentials instanceof ExternalAccountCredentials) {
                ExternalAccountCredentials externalAccountCredentials = (ExternalAccountCredentials) credentials;
                System.out.println("Successfully loaded ExternalAccountCredentials.");

                // You can now use 'externalAccountCredentials' to authenticate with Google Cloud services
                // For example, to get an access token:
                 AccessToken accessToken = externalAccountCredentials.getAccessToken();
                 System.out.println("Access Token: " + accessToken);

                // Initialize BigQuery client with the loaded credentials
                BigQuery bigquery = BigQueryOptions.newBuilder().setCredentials(externalAccountCredentials)
                        .setProjectId(projectId)
                        .build()
                        .getService();

                // Now you can use the 'bigquery' client to interact with BigQuery
                //System.out.println("no MORE wif FILE LOADED...BigQuery client initialized successfully using WIF credentials.");

                // Example: List datasets
                bigquery.listDatasets(projectId).iterateAll()
                        .forEach(dataset -> System.out.println("Dataset: " + dataset.getDatasetId().getDataset()));

            } else {
                System.err.println("The loaded credentials are not ExternalAccountCredentials.");
            }

        } catch (IOException e) {
            System.err.println("Error loading credentials: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
