import React from "react";
import ReactDOM from "react-dom";
// import { composeWithDevTools } from "redux-devtools-extension";
// import rootReducer from "./modules";
import { Provider } from "react-redux";
import axios from "axios";
import App from "./App";

// rtk import
import { store } from "./app/store";

import { tempSetUser } from "./modules/user";

// JWT
axios.defaults.baseURL = "http://54.180.134.20";
axios.defaults.withCredentials = true;

// const store = createStore(rootReducer, composeWithDevTools());

function loadUser() {
	try{
		const user = localStorage.getItem("email");
		if(!user) return; // 로그인 상태가 아니라면 아무것도 안 함

		store.dispatch(tempSetUser(JSON.parse(user)));
	} catch(e) {
		console.log("localStorage is not working");
	}
}

loadUser();

ReactDOM.render(
	<Provider store={store}>
		<App />
	</Provider>,
	document.getElementById("root")
);
