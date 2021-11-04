import React from "react";
import PageTemplate from "../templates/PageTemplate";
import ShopCards from "../UI/organisms/ShopCards";

const ShopMenuPage: React.FC = () => {
	return (
		<PageTemplate header={<h1>한식</h1>} footer={<div>bye</div>}>
			<ShopCards />
		</PageTemplate>
	);
};

export default ShopMenuPage;
