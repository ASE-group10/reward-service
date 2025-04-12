package com.example.reward_service.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EntityTests {

    @Test
    void testCouponEntityDefaultConstructorAndSetters() {
        CouponEntity coupon = new CouponEntity();
        coupon.setCouponId("coupon123");
        coupon.setCouponType("discount");
        coupon.setCouponDesc("20% off on electronics");
        LocalDateTime expiry = LocalDateTime.now().plusDays(5);
        coupon.setCouponExpiryDateAndTime(expiry);
        coupon.setCouponStatus("Active");
        coupon.setCouponRewardPoints(100);
        coupon.setValid(true);

        assertEquals("coupon123", coupon.getCouponId());
        assertEquals("discount", coupon.getCouponType());
        assertEquals("20% off on electronics", coupon.getCouponDesc());
        assertEquals(expiry, coupon.getCouponExpiryDateAndTime());
        assertEquals("Active", coupon.getCouponStatus());
        assertEquals(100, coupon.getCouponRewardPoints());
        assertTrue(coupon.isValid());
    }

    @Test
    void testCouponEntityAllArgsConstructor() {
        LocalDateTime expiry = LocalDateTime.now().plusDays(3);
        // Note: The all-arguments constructor accepts an extra parameter for "redeemed" which is not stored.
        CouponEntity coupon = new CouponEntity("coupon789", "cashback", "Get $10 back", expiry, "Active", 50, false, false);

        assertEquals("coupon789", coupon.getCouponId());
        assertEquals("cashback", coupon.getCouponType());
        assertEquals("Get $10 back", coupon.getCouponDesc());
        assertEquals(expiry, coupon.getCouponExpiryDateAndTime());
        assertEquals("Active", coupon.getCouponStatus());
        assertEquals(50, coupon.getCouponRewardPoints());
        assertFalse(coupon.isValid());
    }

    @Test
    void testRewardEntitySettersAndFieldEquality() {
        RewardEntity reward = new RewardEntity("userX", 200);
        reward.setId(1L);
        reward.setName("Welcome Reward");
        reward.setStatus("Success");

        // Create another RewardEntity with identical field values.
        RewardEntity reward2 = new RewardEntity("userX", 200);
        reward2.setId(1L);
        reward2.setName("Welcome Reward");
        reward2.setStatus("Success");

        // Compare fields individually rather than using object equality.
        assertEquals(reward.getId(), reward2.getId());
        assertEquals(reward.getUserId(), reward2.getUserId());
        assertEquals(reward.getPoints(), reward2.getPoints());
        assertEquals(reward.getStatus(), reward2.getStatus());
        assertEquals(reward.getName(), reward2.getName());
    }

    @Test
    void testTotalRewardsEntityAllArgsConstructorAndSetters() {
        TotalRewardsEntity totalRewards1 = new TotalRewardsEntity("userY", 500);
        assertEquals("userY", totalRewards1.getUserId());
        assertEquals(500, totalRewards1.getTotalPoints());

        TotalRewardsEntity totalRewards2 = new TotalRewardsEntity();
        totalRewards2.setUserId("userY");
        totalRewards2.setTotalPoints(500);

        // If equals/hashCode methods are generated (e.g., via Lombok), these should be equal.
        assertEquals(totalRewards1, totalRewards2);
        assertEquals(totalRewards1.hashCode(), totalRewards2.hashCode());
    }

    @Test
    void testUserCouponEntitySettersAndEquality() {
        UserCouponEntity userCoupon = new UserCouponEntity();
        userCoupon.setId(10L);
        userCoupon.setUserId("userZ");
        userCoupon.setCouponId("couponABC");
        userCoupon.setRedeemed(true);
        LocalDateTime now = LocalDateTime.now();
        userCoupon.setRedeemedAt(now);

        assertEquals(10L, userCoupon.getId());
        assertEquals("userZ", userCoupon.getUserId());
        assertEquals("couponABC", userCoupon.getCouponId());
        assertTrue(userCoupon.isRedeemed());
        assertEquals(now, userCoupon.getRedeemedAt());

        UserCouponEntity userCoupon2 = new UserCouponEntity();
        userCoupon2.setId(10L);
        userCoupon2.setUserId("userZ");
        userCoupon2.setCouponId("couponABC");
        userCoupon2.setRedeemed(true);
        userCoupon2.setRedeemedAt(now);

        // Using Lombok's @Data the objects should be equal.
        assertEquals(userCoupon, userCoupon2);
        assertEquals(userCoupon.hashCode(), userCoupon2.hashCode());
    }
}
