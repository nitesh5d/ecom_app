# ECOM PLUS
## This Ecommerce application has features like 
### -Listing all products from API
### -Login existing user
### -Sort products according to price, rating, popularity
### -Search Products
### -Filter Products according to category or Price
### -Add products to Cart
### -increase and decrease quantity of a particular item in cart.
### -delete an item from cart.
### -Checkout with Pay On Delivery.
### -Razorpay Payment Gateway Integration for Online Payment.

#### This app gets products and users from fakestoreapi.com.

## Flow:
List Products to youser
App launched > splashscreen opened > after two second > if internet connected > homescreen started > api called > on success > all products stored in sqlite database > all products fetched from sqlite and listed to user.

User goes to User Account Activity > check if user token is stored in shared preference > if present User is logged in / if not present show ask user to login via username and password

Features like increase Quantity, Delete product from Cart, Checkout, captcha before checkout, payment integration is not added yet.
