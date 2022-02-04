import React from "react";
import ReactDOM from "react-dom";
// import { composeWithDevTools } from "redux-devtools-extension";
// import rootReducer from "./modules";
import { Provider } from "react-redux";
import axios from "axios";
import App from "./App";

// rtk import
import { store } from "./app/store";

import {
	tempSetID,
	tempSetEmail,
	tempSetName,
	tempSetPhone,
	tempSetRole,
} from "./modules/user";
import {
	getUserID,
	getEmail,
	getName,
	getPhone,
	getRole,
} from "./helpers/userInfoControl";

// JWT
axios.defaults.baseURL = "http://54.180.134.20";
axios.defaults.withCredentials = true;

// const store = createStore(rootReducer, composeWithDevTools());

function loadUser() {
	try {
		const id = getUserID();
		const email = getEmail();
		const name = getName();
		const phone = getPhone();
		const role = getRole();
		console.log("IDDIDIDIDID : ", getUserID());

		if (!id) return; // 로그인 상태가 아니라면 아무것도 안 함

		// 새로고침할 경우에도 로그인 유지 및 유저정보 저장
		store.dispatch(tempSetID(id));
		store.dispatch(tempSetEmail(email));
		store.dispatch(tempSetName(name));
		store.dispatch(tempSetPhone(phone));
		store.dispatch(tempSetRole(role));
	} catch (e) {
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
