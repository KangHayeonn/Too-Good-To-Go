import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import axios from "axios";
import App from "./App";

// rtk import
import { store } from "./app/store";

import { tempSetUser } from "./features/user/userSlice";
import { getUserLocalStorage } from "./helpers/userInfoControl";
// JWT
axios.defaults.baseURL = "http://54.180.134.20";
axios.defaults.withCredentials = true;

function loadUser() {
	try {
		// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
		const parsedUserInfoFromLocalStorage = getUserLocalStorage();
		console.log("IDDIDIDIDID : ", parsedUserInfoFromLocalStorage);

		if (!parsedUserInfoFromLocalStorage) return; // 로그인 상태가 아니라면 아무것도 안 함

		// 새로고침할 경우에도 로그인 유지 및 유저정보 저장
		store.dispatch(tempSetUser(parsedUserInfoFromLocalStorage));
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
