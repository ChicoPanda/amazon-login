package com.acme2.service.impl;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AwsServiceImpl {

	public static final String COLLECTIONID = "facesAcme2";
	  AWSCredentials credentials;
	ByteBuffer sourceImageBytes = null;
	AmazonRekognition amazonRekognition; 
	 
	AwsServiceImpl() {
		try {
			credentials = new ProfileCredentialsProvider("ACME2").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
		}
		amazonRekognition = AmazonRekognitionClientBuilder.standard().withRegion(Regions.US_WEST_2)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}

	public static byte[] convertToImg(String base64) {
		return Base64.decodeBase64(base64);
	}

	public String addFaceImage(String base64, String faceName) {
		String faceId = null;
		byte[] base64Val = convertToImg(base64);
		sourceImageBytes = ByteBuffer.wrap(base64Val);

		Image image = new Image().withBytes(sourceImageBytes);

		IndexFacesRequest indexFacesRequest = new IndexFacesRequest().withImage(image).withCollectionId(COLLECTIONID)
				.withExternalImageId(faceName).withDetectionAttributes("ALL");

		IndexFacesResult indexFacesResult = amazonRekognition.indexFaces(indexFacesRequest);

		List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
		for (FaceRecord faceRecord : faceRecords) {
			faceRecord.getFace().getFaceId();
			faceId = faceRecord.getFace().getFaceId();
		}
		return faceId;
	}

	public String compareFace(String base64) {
		String faceId = null;
		ObjectMapper objectMapper = new ObjectMapper();

		byte[] base64Val = convertToImg(base64);
		sourceImageBytes = ByteBuffer.wrap(base64Val);
		Image image = new Image().withBytes(sourceImageBytes);
		SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
				.withCollectionId(COLLECTIONID).withImage(image).withFaceMatchThreshold(85F).withMaxFaces(1);		 

		SearchFacesByImageResult searchFacesByImageResult = amazonRekognition
				.searchFacesByImage(searchFacesByImageRequest);
		List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();
		for (FaceMatch face : faceImageMatches) {
			try {
				faceId = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face.getFace().getFaceId());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		
		}
		 
		return faceId;
	}
}
