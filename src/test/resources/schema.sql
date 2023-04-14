CREATE TABLE IF NOT EXISTS social_network_posts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_date  DATE,
    author     VARCHAR(255),
    content    VARCHAR(255),
    view_count NUMERIC
);
