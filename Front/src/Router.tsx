import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Shop from "./pages/Shop/Shop";
import ShopMenuPage from "./pages/ShopMenu/ShopMenuPage";

const Router: React.FC = () => {
	return (
		<BrowserRouter>
			<Switch>
				<Route path="/shopmenu" component={ShopMenuPage} />
				<Route path="/shop" component={Shop} />
				<Route path="/login" component={Login} />
				<Route path="/register" component={Register} />
				<Route render={() => <div>에러페이지</div>} />
			</Switch>
		</BrowserRouter>
	);
};

export default Router;
