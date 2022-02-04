import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import axios from "axios";
import App from "./App";

// rtk import
import { store } from "./app/store";

import { tempSetUser } from "./features/user/userSlice";
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
		store.dispatch(tempSetUser({ id, email, name, phone, role }));
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
