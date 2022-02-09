import React from "react";
import axios from "axios";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Header from "./components/organisms/Header/Header";
import Login from "./pages/Login/LoginPage";
import Register from "./pages/Register/RegisterPage";
import Shop from "./pages/Shop/ShopPage";
import ShopMenuPage from "./pages/ShopMenu/ShopMenuPage";
import Footer from "./components/organisms/Footer/Footer";
import CartPage from "./pages/Cart/CartPage";
import PostTest from "./pages/Test/PostTest";
import MainPage from "./pages/Main/MainPage";
import Manager from "./pages/Manager/Manager";
import OrderPage from "./pages/Order/OrderPage";
import PayPage from "./pages/Pay/PayPage";
import ProfilePage from "./pages/profilePage/ProfilePage";
import { getAccessToken } from "./helpers/tokenControl";

const Router: React.FC = () => {
	// Storing session token into request header on refresh.
	React.useEffect(() => {
		const token = getAccessToken();
		if (token) {
			axios.defaults.headers.common.Authorization = token;
		}
	}, []);

	return (
		<BrowserRouter>
			<Header />
			<Switch>
				<Route exact path="/" component={MainPage} />
				<Route exact path="/shopmenu/" component={ShopMenuPage} />
				<Route path="/shopmenu/:menuName" component={ShopMenuPage} />
				<Route exact path="/shop" component={Shop} />
				<Route path="/shop/:shopId" component={Shop} />
				<Route exact path="/login" component={Login} />
				<Route exact path="/register" component={Register} />
				<Route exact path="/cart" component={CartPage} />
				<Route exact path="/apitest" component={PostTest} />
				<Route exact path="/manager" component={Manager} />
				<Route path="/manager/:statusName" component={Manager} />
				<Route exact path="/order" component={OrderPage} />
				<Route exact path="/pay" component={PayPage} />
				<Route exact path="/profile" component={ProfilePage} />
				<Route render={() => <div>에러페이지</div>} />
			</Switch>
			<Footer />
		</BrowserRouter>
	);
};

export default Router;
