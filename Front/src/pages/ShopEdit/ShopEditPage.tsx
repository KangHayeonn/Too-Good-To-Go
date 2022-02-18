import React from "react";
import { RouteComponentProps } from "react-router-dom";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import Categories from "../../components/organisms/Categories/Categories";
import ShopMenuHeader from "../../components/molecules/Shop/ShopMenuHeader";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";
import CartCardsEdit from "../../components/molecules/Shop/CartCardsEdit";

interface matchParams {
	shopId: string;
}

const ShopEditPage: React.FC<RouteComponentProps<matchParams>> = ({
	match,
}) => {
	const { shopId } = match.params;
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			section={<ShopTitles shopMatchId={shopId} isEdit />}
			isSection
			isFooter={false}
		>
			<ShopMenuHeader isEdit shopMatchId={shopId}>
				메뉴
			</ShopMenuHeader>
			<ShopTemplate
				menu={
					<div>
						<CartCardsEdit shopMatchId={shopId} />
					</div>
				}
				isSide={false}
			/>
		</PageTemplate>
	);
};

export default ShopEditPage;
