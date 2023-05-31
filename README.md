# ECOM PLUS
## This Ecommerce application has features like 
### -Listing all products from API
### -Login existing user
### -Sort products according to price, rating, popularity
### -Search Products
### -Filter Products according to category or Price
### -Add products to Cart
### -Increase and decrease item quantity in cart.
### -Delete an item from cart.
### -Checkout with Pay On Delivery.
### -Razorpay Payment Gateway Integration for Online Payment.

## Flow:
#### [This app gets products and users auth from fakestoreapi.com.]

#### List Products -
App launched >> splashscreen opened >> after two second >> if internet connected >> homescreen started >> API called >> on success >> all products stored in sqlite database >> all products fetched from sqlite and listed to user.

#### Login -
User opened User Activity >> check if user token is stored in shared preference >> if token present, User is logged in / if not present, Ask user to login via username and password >> User enters username & password >> API called >> on success >> User token stored in Shared Preference >> Activity closed.

#### Place an Order - 
User selects a Product >> Item added to Cart >> Cart Activity opened >> adds/deletes quantity >> User clicks Checkout >> All item to be bought displayed >> Total Amount displayed >> User selects Payment method >> User types 'Confirm Order' >> Place Order Clicked >> get selected Payment method >> product Id, product quantity and user auth token taken >> if User has selected 'Pay on Delivery' >> insert new row in 'orders' table >> if insertion successful >> Tell User 'Order Placed', take user to home page, remove all products from cart.     if User has selected 'Online' >> Take User to Razorpay Payment Gateway with details like amount, contact, email, etc >> onPaymentSuccess >> insert new row in 'orders' table >> if insertion successful >> Tell User 'Order Placed', take user to home page, remove all products from cart.

## Tools and Technologies used-
#### ` Android Studio ` ` Java ` ` SQLite ` `Retrofit` `Glide` `Razorpay Payments`

## Screenshots
![screenshots](https://github.com/nitesh5d/ecom_app/assets/62883847/9d78cbae-2950-40ea-9202-c98ab9f0ef4f)
