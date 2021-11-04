import React from "react";
import "./App.css";
import Header from "./components/js/Header";
import Footer from "./components/js/Footer";
import ShopMenuPage from "./components/pages/ShopMenuPage";

const App: React.FC = () => {
	return (
		<div className="App">
			<Header />
			<ShopMenuPage />
			<Footer />
		</div>
	);
};

export default App;
