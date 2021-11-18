import React from "react";
import "./App.css";
// Experiment on provider wrapping Router component. may need to delete, and move provider to a different location afterwards.
import Router from "./Router";

const App: React.FC = () => {
	return (
		<div className="App">
			<Router />
		</div>
	);
};

export default App;
