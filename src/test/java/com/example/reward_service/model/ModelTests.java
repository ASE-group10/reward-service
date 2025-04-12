package com.example.reward_service.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ModelTests {

    @Test
    void testCouponInfoConstructorAndGetters() {
        LocalDateTime expiry = LocalDateTime.now().plusDays(1);
        CouponInfo coupon = new CouponInfo("coupon1", "discount", "10% off", expiry, "Active", 10);
        assertEquals("coupon1", coupon.getCouponId());
        assertEquals("discount", coupon.getCouponType());
        assertEquals("10% off", coupon.getCouponDesc());
        assertEquals(expiry, coupon.getExpiryDate());
        assertEquals("Active", coupon.getStatus());
        assertEquals(10, coupon.getRequiredPoints());
    }

    @Test
    void testCouponInfoSettersAndToString() {
        CouponInfo coupon = new CouponInfo("coupon1", "discount", "10% off",
                LocalDateTime.now().plusDays(1), "Active", 10);
        coupon.setCouponDesc("15% off");
        assertEquals("15% off", coupon.getCouponDesc());
        assertNotNull(coupon.toString());
    }

    @Test
    void testCouponInfoEquality() {
        LocalDateTime expiry = LocalDateTime.now().plusDays(1);
        CouponInfo coupon1 = new CouponInfo("coupon1", "discount", "10% off", expiry, "Active", 10);
        CouponInfo coupon2 = new CouponInfo("coupon1", "discount", "10% off", expiry, "Active", 10);
        // Using Lombok-generated equals and hashCode
        assertEquals(coupon1, coupon2);
        assertEquals(coupon1.hashCode(), coupon2.hashCode());
    }

    @Test
    void testRedeemRequestDefaultSetter() {
        RedeemRequest request = new RedeemRequest();
        request.setCouponId("coupon123");
        assertEquals("coupon123", request.getCouponId());
    }

    @Test
    void testRewardModelConstructorAndSetters() {
        Reward reward = new Reward("user1", 50, "Valid");
        assertEquals("user1", reward.getUserId());
        assertEquals(50, reward.getPoints());
        assertEquals("Valid", reward.getStatus());
        reward.setPoints(75);
        assertEquals(75, reward.getPoints());
    }

    @Test
    void testRewardEquality() {
        Reward reward1 = new Reward("user1", 50, "Valid");
        Reward reward2 = new Reward("user1", 50, "Valid");
        // Lombok @Data generates equals and hashCode based on fields.
        assertEquals(reward1, reward2);
        assertEquals(reward1.hashCode(), reward2.hashCode());
    }

    @Test
    void testRewardToString() {
        Reward reward = new Reward("user1", 50, "Valid");
        String str = reward.toString();
        assertNotNull(str);
        assertTrue(str.contains("user1"));
    }

    @Test
    void testRewardRequestAndInnerRouteDetails() {
        RewardRequest request = new RewardRequest();
        request.setUserId("user2");

        // Test the inner static class RouteDetails inside RewardRequest.
        RewardRequest.RouteDetails innerRd = new RewardRequest.RouteDetails();
        innerRd.setDistance(3.5);
        innerRd.setHealthCompliant(true);
        request.setRouteDetails(innerRd);

        assertEquals("user2", request.getUserId());
        assertNotNull(request.getRouteDetails());
        assertEquals(3.5, request.getRouteDetails().getDistance());
        assertTrue(request.getRouteDetails().isHealthCompliant());
    }

    @Test
    void testRewardRequestEquality() {
        RewardRequest request1 = new RewardRequest();
        request1.setUserId("user3");
        RewardRequest.RouteDetails rd1 = new RewardRequest.RouteDetails();
        rd1.setDistance(2.0);
        rd1.setHealthCompliant(false);
        request1.setRouteDetails(rd1);

        RewardRequest request2 = new RewardRequest();
        request2.setUserId("user3");
        RewardRequest.RouteDetails rd2 = new RewardRequest.RouteDetails();
        rd2.setDistance(2.0);
        rd2.setHealthCompliant(false);
        request2.setRouteDetails(rd2);

        // Verify the two RewardRequests are equal (based on Lombok @Data)
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testRouteDetailsSettersAndEquality() {
        RouteDetails details1 = new RouteDetails();
        details1.setDistance(5.0);
        details1.setHealthCompliant(false);

        RouteDetails details2 = new RouteDetails();
        details2.setDistance(5.0);
        details2.setHealthCompliant(false);

        assertEquals(5.0, details1.getDistance());
        assertFalse(details1.isHealthCompliant());

        // Compare field values manually:
        assertEquals(details1.getDistance(), details2.getDistance());
        assertEquals(details1.isHealthCompliant(), details2.isHealthCompliant());
    }
}
