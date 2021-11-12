import React from "react";
import "./App.css";
// Experiment on provider wrapping Router component. may need to delete, and move provider to a different location afterwards.
import { Provider } from "react-redux";
import { store } from "./CartReducer/state";
import Router from "./Router";

const App: React.FC = () => {
	return (
		<div className="App">
			<Provider store={store}>
				<Router />
			</Provider>
		</div>
	);
};

export default App;
