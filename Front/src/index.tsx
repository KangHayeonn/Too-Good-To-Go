import React from "react";
import ReactDOM from "react-dom";
// import { composeWithDevTools } from "redux-devtools-extension";
// import rootReducer from "./modules";
import { Provider } from "react-redux";
import axios from "axios";
import App from "./App";

// rtk import
import { store } from "./app/store";

// JWT
axios.defaults.baseURL = "http://54.180.134.20";
axios.defaults.withCredentials = true;

// const store = createStore(rootReducer, composeWithDevTools());

ReactDOM.render(
	<Provider store={store}>
		<App />
	</Provider>,
	document.getElementById("root")
);
