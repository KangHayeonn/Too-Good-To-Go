import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Header from "./components/organisms/Header/Header";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Shop from "./pages/Shop/Shop";
import ShopMenuPage from "./pages/ShopMenu/ShopMenuPage";
import Footer from "./components/organisms/Footer/Footer";
import CartPage from "./pages/Cart/CartPage";

const Router: React.FC = () => {
	return (
		<BrowserRouter>
			<Header />
			<Switch>
				<Route path="/shopmenu" component={ShopMenuPage} />
				<Route path="/shop" component={Shop} />
				<Route path="/login" component={Login} />
				<Route path="/register" component={Register} />
				<Route path="/cart" component={CartPage} />
				<Route render={() => <div>에러페이지</div>} />
			</Switch>
			<Footer />
		</BrowserRouter>
	);
};

export default Router;
