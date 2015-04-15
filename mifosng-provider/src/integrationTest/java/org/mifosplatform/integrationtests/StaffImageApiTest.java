/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.integrationtests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mifosplatform.integrationtests.common.ImageHelper;
import org.mifosplatform.integrationtests.common.Utils;
import org.mifosplatform.integrationtests.common.organisation.StaffHelper;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class StaffImageApiTest {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @Before
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test
    public void createStaffImage() {
        createTestImage();
    }

    @Test
    public void getStaffImage() {
        Integer staffId = createTestImage();
        String imageAsText = ImageHelper.getStaffImageAsText(this.requestSpec, this.responseSpec, staffId);
        Assert.assertNotNull("Image id should not be null", imageAsText);
    }

    @Test
    public void getStaffImageAsBinary() {
        Integer staffId = createTestImage();
        byte[] imageAsBytes = ImageHelper.getStaffImageAsBinary(this.requestSpec, this.responseSpec, staffId);
        Assert.assertNotNull("Image content should not be null", imageAsBytes);
    }

    @Test
    public void updateImage() {
        Integer staffId = createTestImage();
        Integer imageId = ImageHelper.updateImageForStaff(this.requestSpec, this.responseSpec, staffId);
        Assert.assertNotNull("Image id should not be null", imageId);
    }

    @Test
    public void deleteStaffImage() {
        Integer staffId = createTestImage();
        Integer imageId = ImageHelper.deleteStaffImage(this.requestSpec, this.responseSpec, staffId);
        Assert.assertNotNull("Image id should not be null", imageId);
    }

    private Integer createTestImage() {
        Integer staffId = StaffHelper.createStaff(this.requestSpec, this.responseSpec);
        Integer imageId = ImageHelper.createImageForStaff(this.requestSpec, this.responseSpec, staffId);
        Assert.assertNotNull("Image id should not be null", imageId);
        return staffId;
    }

}
