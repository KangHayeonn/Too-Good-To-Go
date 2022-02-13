import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import Categories from "../../components/organisms/Categories/Categories";
import ShopMenuHeader from "../../components/molecules/Shop/ShopMenuHeader";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";
import CartCardsEdit from "../../components/molecules/Shop/CartCardsEdit";

const Shop: React.FC = () => {
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			section={<ShopTitles />}
			isSection
			isFooter={false}
		>
			<ShopMenuHeader>메뉴</ShopMenuHeader>
			<ShopTemplate
				menu={
					<div>
						<CartCardsEdit />
					</div>
				}
				isSide={false}
			/>
		</PageTemplate>
	);
};

export default Shop;
