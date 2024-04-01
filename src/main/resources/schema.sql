CREATE TABLE `wrapper`
(
    `wrapper_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(50) NOT NULL,
    `cost`       INT         NOT NULL,
    PRIMARY KEY (`wrapper_id`)
);

CREATE TABLE `tag`
(
    `tag_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(50) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`tag_id`)
);

CREATE TABLE `author`
(
    `author_id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(30) NOT NULL,
    `description` TEXT        NOT NULL,
    PRIMARY KEY (`author_id`)
);

CREATE TABLE `users`
(
    `user_id`    BIGINT       NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(30)  NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `registered` TINYINT(1)   NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `file`
(
    `file_id`    BIGINT       NOT NULL AUTO_INCREMENT,
    `url`        VARCHAR(200) NOT NULL,
    `extension`  VARCHAR(10)  NOT NULL,
    `created_at` DATETIME     NOT NULL,
    PRIMARY KEY (`file_id`)
);

CREATE TABLE `book_file_type`
(
    `book_file_type_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(10) NOT NULL,
    PRIMARY KEY (`book_file_type_id`)
);

CREATE TABLE `order_status`
(
    `order_status_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(20) NOT NULL,
    PRIMARY KEY (`order_status_id`)
);

CREATE TABLE `role`
(
    `role_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(20) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `coupon_policy`
(
    `coupon_policy_id`  BIGINT      NOT NULL AUTO_INCREMENT,
    `min_order_cost`    INT         NOT NULL,
    `discount_type`     VARCHAR(20) NOT NULL,
    `discount_cost`     INT         NOT NULL,
    `max_discount_cost` INT         NULL,
    PRIMARY KEY (`coupon_policy_id`)
);

CREATE TABLE `coupon_category`
(
    `coupon_category_id` BIGINT NOT NULL AUTO_INCREMENT,
    `coupon_id`          BIGINT NOT NULL,
    `category_id`        BIGINT NOT NULL,
    PRIMARY KEY (`coupon_category_id`)
);

CREATE TABLE `payment_method`
(
    `payment_method_id` BIGINT       NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(100) NOT NULL,
    PRIMARY KEY (`payment_method_id`)
);

CREATE TABLE `publisher`
(
    `publisher_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(50) NOT NULL,
    PRIMARY KEY (`publisher_id`)
);

CREATE TABLE `point_rate`
(
    `point_rate_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(30) NOT NULL,
    `earn_type`     VARCHAR(20) NOT NULL,
    `earn_point`    INT         NOT NULL,
    `apply_type`    VARCHAR(20) NOT NULL,
    `created_at`    DATETIME    NOT NULL,
    PRIMARY KEY (`point_rate_id`)
);

CREATE TABLE `delete_reason_category`
(
    `delete_reason_category_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`                      VARCHAR(50) NOT NULL,
    PRIMARY KEY (`delete_reason_category_id`)
);

CREATE TABLE `category`
(
    `category_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(20) NOT NULL,
    `status`      VARCHAR(20) NOT NULL,
    `parent_id`   BIGINT      NULL,
    PRIMARY KEY (`category_id`),
    FOREIGN KEY (`parent_id`) REFERENCES `category` (`category_id`)
);

CREATE TABLE `refund_category`
(
    `refund_category_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`               VARCHAR(50) NOT NULL,
    PRIMARY KEY (`refund_category_id`)
);

CREATE TABLE `delete_reason`
(
    `delete_reason_id`          BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`                   BIGINT NOT NULL,
    `delete_reason_category_id` BIGINT NOT NULL,
    PRIMARY KEY (`delete_reason_id`),
    FOREIGN KEY (`delete_reason_category_id`) REFERENCES `delete_reason_category` (`delete_reason_category_id`)
);

CREATE TABLE `ranks`
(
    `rank_id`       BIGINT      NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(20) NOT NULL,
    `point_rate_id` BIGINT      NOT NULL,
    PRIMARY KEY (`rank_id`),
    FOREIGN KEY (`point_rate_id`) REFERENCES `point_rate` (`point_rate_id`)
);

CREATE TABLE `member`
(
    `user_id`      BIGINT      NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(50) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `birth_date`   DATE        NOT NULL,
    `nickname`     VARCHAR(30) NOT NULL,
    `point`        INT         NOT NULL,
    `status`       VARCHAR(20) NOT NULL,
    `rank_id`      BIGINT      NOT NULL,
    `role_id`      BIGINT      NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`rank_id`) REFERENCES `ranks` (`rank_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
);

CREATE TABLE `orders`
(
    `order_id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `order_number`    VARCHAR(64)  NOT NULL,
    `order_info`      VARCHAR(100) NOT NULL,
    `order_date`      DATETIME     NOT NULL,
    `name`            VARCHAR(50)  NOT NULL,
    `phone_number`    VARCHAR(20)  NOT NULL,
    `total_cost`      BIGINT       NOT NULL,
    `used_point`      INT          NOT NULL,
    `user_id`         BIGINT       NOT NULL,
    `order_status_id` BIGINT       NOT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`),
    FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`order_status_id`)
);

CREATE TABLE `delivery`
(
    `order_id`         BIGINT       NOT NULL,
    `name`             VARCHAR(50)  NOT NULL,
    `phone_number`     VARCHAR(20)  NOT NULL,
    `delivery_address` VARCHAR(200) NOT NULL,
    `delivery_cost`    BIGINT       NOT NULL,
    `estimated_date`   DATE         NOT NULL,
    `complete_date`    DATE         NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
);

CREATE TABLE `coupon`
(
    `coupon_id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50) NOT NULL,
    `coupon_policy_id` BIGINT      NOT NULL,
    PRIMARY KEY (`coupon_id`),
    FOREIGN KEY (`coupon_policy_id`) REFERENCES `coupon_policy` (`coupon_policy_id`)
);

CREATE TABLE `member_coupon`
(
    `member_coupon_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `status`           VARCHAR(20) NOT NULL,
    `issue_date`       DATE        NOT NULL,
    `expire_date`      DATE        NOT NULL,
    `use_date`         DATE        NULL,
    `coupon_id`        BIGINT      NOT NULL,
    PRIMARY KEY (`member_coupon_id`),
    FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`)
);

CREATE TABLE `order_coupon`
(
    `order_coupon_id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id`        BIGINT NOT NULL,
    `coupon_id`       BIGINT NOT NULL,
    PRIMARY KEY (`order_coupon_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`)
);

CREATE TABLE `address`
(
    `address_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `nickname`        VARCHAR(50)  NULL,
    `address_number`  VARCHAR(5)   NOT NULL,
    `location`        VARCHAR(200) NOT NULL,
    `default_address` TINYINT(1)   NOT NULL,
    `user_id`         BIGINT       NOT NULL,
    PRIMARY KEY (`address_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `point_history`
(
    `point_history_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `change_point`     INT         NOT NULL,
    `change_reason`    VARCHAR(50) NOT NULL,
    `change_date`      DATETIME    NOT NULL,
    `user_id`          BIGINT      NOT NULL,
    PRIMARY KEY (`point_history_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`)
);

CREATE TABLE `cart`
(
    `cart_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`cart_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`)
);

CREATE TABLE `book`
(
    `book_id`       BIGINT      NOT NULL AUTO_INCREMENT,
    `title`         VARCHAR(50) NOT NULL,
    `description`   TEXT        NOT NULL,
    `book_index`    TEXT        NULL,
    `pub_date`      DATE        NOT NULL,
    `isbn_10`       VARCHAR(10) NULL,
    `isbn_13`       VARCHAR(13) NULL,
    `cost`          INT         NOT NULL,
    `discount_cost` INT         NOT NULL,
    `packagable`    TINYINT(1)  NOT NULL,
    `views`         BIGINT      NOT NULL,
    `status`        VARCHAR(20) NOT NULL,
    `stock`         INT         NOT NULL,
    `publisher_id`  BIGINT      NOT NULL,
    `point_rate_id` BIGINT      NOT NULL,
    PRIMARY KEY (`book_id`),
    FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`),
    FOREIGN KEY (`point_rate_id`) REFERENCES `point_rate` (`point_rate_id`)
);

CREATE TABLE `review`
(
    `review_id`     BIGINT   NOT NULL AUTO_INCREMENT,
    `rate`          INT      NOT NULL,
    `content`       TEXT     NOT NULL,
    `created_at`    DATETIME NOT NULL,
    `book_id`       BIGINT   NOT NULL,
    `point_rate_id` BIGINT   NOT NULL,
    `user_id`       BIGINT   NOT NULL,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`point_rate_id`) REFERENCES `point_rate` (`point_rate_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`)
);

CREATE TABLE `review_image`
(
    `review_image_id` BIGINT NOT NULL AUTO_INCREMENT,
    `review_id`       BIGINT NOT NULL,
    `file_id`         BIGINT NOT NULL,
    PRIMARY KEY (`review_image_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
    FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`)
);

CREATE TABLE `book_file`
(
    `book_file_id`      BIGINT NOT NULL AUTO_INCREMENT,
    `book_id`           BIGINT NOT NULL,
    `book_file_type_id` BIGINT NOT NULL,
    `file_id`           BIGINT NOT NULL,
    PRIMARY KEY (`book_file_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`book_file_type_id`) REFERENCES `book_file_type` (`book_file_type_id`),
    FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`)
);

CREATE TABLE `book_author`
(
    `book_author_id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id`        BIGINT NOT NULL,
    `author_id`      BIGINT NOT NULL,
    PRIMARY KEY (`book_author_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`)
);

CREATE TABLE `order_book`
(
    `order_book_id`  BIGINT      NOT NULL AUTO_INCREMENT,
    `order_quantity` INT         NOT NULL,
    `packaging`      TINYINT(1)  NOT NULL,
    `status`         VARCHAR(20) NOT NULL,
    `book_id`        BIGINT      NOT NULL,
    `order_id`       BIGINT      NOT NULL,
    `wrapper_id`     BIGINT      NOT NULL,
    PRIMARY KEY (`order_book_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    FOREIGN KEY (`wrapper_id`) REFERENCES `wrapper` (`wrapper_id`)
);

CREATE TABLE `wish`
(
    `wish_id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`wish_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`user_id`) REFERENCES `member` (`user_id`)
);

CREATE TABLE `book_tag`
(
    `book_tag_id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id`     BIGINT NOT NULL,
    `tag_id`      BIGINT NOT NULL,
    PRIMARY KEY (`book_tag_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
);

CREATE TABLE `book_category`
(
    `book_category_id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id`          BIGINT NOT NULL,
    `category_id`      BIGINT NOT NULL,
    PRIMARY KEY (`book_category_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

CREATE TABLE `payment`
(
    `order_id`          BIGINT       NOT NULL,
    `payment_cost`      INT          NOT NULL,
    `payment_key`       VARCHAR(200) NOT NULL,
    `payment_method_id` BIGINT       NOT NULL,
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`payment_method_id`)
);

CREATE TABLE `refund`
(
    `refund_id`          BIGINT NOT NULL AUTO_INCREMENT,
    `reason`             TEXT   NOT NULL,
    `quantity`           INT    NOT NULL,
    `refund_category_id` BIGINT NOT NULL,
    `order_book_id`      BIGINT NOT NULL,
    PRIMARY KEY (`refund_id`),
    FOREIGN KEY (`refund_category_id`) REFERENCES `refund_category` (`refund_category_id`),
    FOREIGN KEY (`order_book_id`) REFERENCES `order_book` (`order_book_id`)
);

CREATE TABLE `cart_item`
(
    `cart_item_id` BIGINT NOT NULL AUTO_INCREMENT,
    `quantity`     INT    NOT NULL,
    `cart_id`      BIGINT NOT NULL,
    `book_id`      BIGINT NOT NULL,
    PRIMARY KEY (`cart_item_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
);

CREATE TABLE `coupon_book`
(
    `coupon_book_id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id`        BIGINT NOT NULL,
    `coupon_id`      BIGINT NOT NULL,
    PRIMARY KEY (`coupon_book_id`),
    FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
    FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`)
);
