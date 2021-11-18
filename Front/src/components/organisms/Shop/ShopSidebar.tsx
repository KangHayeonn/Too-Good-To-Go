import React from "react";
import styled from "@emotion/styled";
import ShopCard from "../../molecules/ShopMenu/ShopCard";

const ShopList = styled.ul`
	width: 100%;
	height: 100%;
	display: flex;
	flex-wrap: wrap;
`;

const ShopListLi = styled.li`
	width: calc(20% - 20px);
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
		shopId: 0,
		shopFoodImg: "image/food2.jpg",
		shopName: "전주비빔밥",
		shopFoodName: "전통 전주비빔밥",
		shopFoodSale: 10,
		shopFoodCost: 9000,
		shopBeforeCost: 10000,
	},
];

const ShopCards: React.FC = () => {
	return (
		<ShopList>
			{shops.map((row) => (
				<ShopListLi>
					<ShopCard
						key={row.shopId}
						shopName={row.shopName}
						shopFoodImg={row.shopFoodImg}
						shopFoodName={row.shopFoodName}
						shopFoodSale={row.shopFoodSale}
						shopFoodCost={row.shopFoodCost}
						shopBeforeCost={row.shopBeforeCost}
					/>
				</ShopListLi>
			))}
		</ShopList>
	);
};

export default ShopCards;