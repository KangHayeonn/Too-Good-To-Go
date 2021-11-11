import React, { useEffect, useState } from "react";
import axios from "axios";
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

type shopsDataType = {
	id: number;
	image: string;
	shopName: string;
	productName: string;
	shopFoodSale: number;
	discountPrice: number;
	price: number;
	category: string;
	data: Array<string | number>;
};

const SHOP_BOARD_API_BASE_URL = "http://localhost:8080/api/shopboards";
const BoardService = () => {
	return axios.get(SHOP_BOARD_API_BASE_URL);
};

const calculatedDiscount = (price: number, discountPrice: number): number => {
	return Math.ceil((1 - discountPrice / price) * 100);
};

interface menuMatchType {
	menuMatchName?: string;
}

const ShopCards: React.FC<menuMatchType> = ({ menuMatchName }) => {
	const [shop, setShops] = useState<shopsDataType[]>([]);
	useEffect(() => {
		BoardService().then((res) => {
			setShops(res.data.data);
		});
	}, []);
	return (
		<ShopList>
			{shop.map((row) =>
				row.category === menuMatchName ? (
					<ShopListLi key={row.id}>
						<ShopCard
							shopName={row.shopName}
							shopFoodImg={row.image}
							shopFoodName={row.productName}
							shopFoodSale={calculatedDiscount(
								row.price,
								row.discountPrice
							)}
							shopFoodCost={row.discountPrice}
							shopBeforeCost={row.price}
						/>
					</ShopListLi>
				) : null
			)}
		</ShopList>
	);
};

ShopCards.defaultProps = {
	menuMatchName: "한식",
};

export default ShopCards;
