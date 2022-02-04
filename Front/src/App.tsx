import React from "react";
import "./App.css";
// Experiment on provider wrapping Router component. may need to delete, and move provider to a different location afterwards.
import axios from "axios";
import Router from "./Router";

const App: React.FC = () => {
	console.log(
		"App.tsx axios.header: ",
		axios.defaults.headers.common.Authorization
	);
	return (
		<div className="App">
			<Router />
		</div>
	);
};

export default App;
