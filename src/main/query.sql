CREATE TABLE payment (
	"payment_id" SERIAL PRIMARY KEY,
	"amount" INTEGER NOT NULL,
	"payment_type_id" BIGINT NOT NULL,
	"date" Date DEFAULT now(),
	"customer_id" BIGINT NOT NULL
);

CREATE TABLE payment_type (
	"payment_type_id" SERIAL PRIMARY KEY,
	"type_name" VARCHAR NOT NULL
);

CREATE TABLE inventory (
	"item_id" SERIAL PRIMARY KEY,
	"item_name" VARCHAR NOT NULL,
	"quantity" INTEGER NOT NULL,
	"price" INTEGER NOT NULL
);