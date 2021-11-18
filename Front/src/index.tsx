import React from "react";
import ReactDOM from "react-dom";
// import { composeWithDevTools } from "redux-devtools-extension";
// import rootReducer from "./modules";
import { Provider } from "react-redux";
import App from "./App";

// rtk import
import { store } from "./app/store";

// const store = createStore(rootReducer, composeWithDevTools());

ReactDOM.render(
	<Provider store={store}>
		<App />
	</Provider>,
	document.getElementById("root")
);
