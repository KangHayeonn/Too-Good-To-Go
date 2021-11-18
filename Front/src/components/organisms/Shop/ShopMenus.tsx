import React from "react";
import styled from "@emotion/styled";
import ShopMenu from "../../molecules/Shop/ShopMenu";

const ShopList = styled.ul`
	width: 100%;
	height: 100%;
	//display: flex;
	flex-wrap: wrap;
`;

const ShopListLi = styled.li`
	width: calc(100%);
	position: relative;
	margin-bottom: 40px;
	&:nth-of-type(5n) {
		margin-right: 0;
	}
	&:not(:nth-of-type(5n)) {
		margin-right: 25px;
	}
`;

const shops = [
	{
		shopId: 8,
		shopFoodImg: "image/food2.jpg",
		shopName: "전주비빔밥",
		shopFoodName: "전통 전주비빔밥",
	},
	{
		shopId: 9,
		shopFoodImg: "image/food3.jpg",
		shopName: "전통 부대찌개 맛집",
		shopFoodName: "맛있는 부대찌개",
	},
];

const ShopMenus: React.FC = () => {
	return (
		<ShopList>
			{shops.map((row) => (
				<ShopListLi>
					<ShopMenu
						key={row.shopId}
						shopName={row.shopName}
						shopFoodImg={row.shopFoodImg}
						shopFoodName={row.shopFoodName}
					/>
				</ShopListLi>
			))}
		</ShopList>
	);
};

export default ShopMenus;
