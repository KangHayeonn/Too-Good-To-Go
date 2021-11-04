import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopCards from "../../components/organisms/ShopMenu/ShopCards";
import ShopMenuHeader from "../../components/molecules/ShopMenu/ShopMenuHeader";
import ShopHeaderMenu from "../../components/organisms/ShopMenu/ShopHeaderMenu";

const ShopMenuPage: React.FC = () => {
	return (
		<PageTemplate
			header={<ShopHeaderMenu />}
			footer={<div>페이지네이션</div>}
		>
			<ShopMenuHeader>한식</ShopMenuHeader>
			<ShopCards />
		</PageTemplate>
	);
};

export default ShopMenuPage;
