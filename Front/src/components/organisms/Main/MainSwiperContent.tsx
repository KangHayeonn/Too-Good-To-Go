import React, { useState, useEffect } from "react";
import axios from "axios";
import styled from "@emotion/styled";
import ShopCard from "../../molecules/ShopMenu/ShopCard";

const ShopList = styled.ul`
	width: 100%;
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
`;

const ShopListLi = styled.li`
	width: calc(20% - 30px);
	position: relative;
	&:nth-of-type(5n) {
		margin-right: 0;
	}
	&:not(:nth-of-type(5n)) {
		margin-right: 70px;
	}
	&:first-of-type {
		margin-left: 70px;
	}
	margin-bottom: 50px;
`;

type shopsDataType = {
	id: number;
	image: string;
	name: string;
	discountedPrice: number;
	price: number;
	shopId: number;
	shopName: string;
	data?: Array<string | number>;
};

// Uncomment when PRODUCT 추천조회 GET is completed.
// const SHOP_BOARD_API_BASE_URL =
// 	"http://54.180.134.20/api/products/recommend";

const SHOP_BOARD_API_BASE_URL = "http://54.180.134.20/api/products/recommend";
const BoardService = () => {
	return axios.get(SHOP_BOARD_API_BASE_URL);
};

const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

type slice = {
	start: number;
	last: number;
};

const MainSwiperContent: React.FC<slice> = ({ start, last }) => {
	const [shop, setShops] = useState<shopsDataType[]>([]);
	useEffect(() => {
		BoardService().then((res) => {
			setShops(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
		});
	}, []);
	return (
		<ShopList>
			{shop &&
				shop.slice(start, last).map((row) => (
					<ShopListLi key={row.id}>
						<ShopCard
							shopId={row.shopId}
							shopName={row.shopName}
							shopFoodImg={row.image}
							shopFoodName={row.name}
							shopFoodSale={calculatedDiscount(
								row.price,
								row.discountedPrice
							)}
							shopFoodCost={row.discountedPrice}
							shopBeforeCost={row.price}
						/>
					</ShopListLi>
				))}
		</ShopList>
	);
};

export default MainSwiperContent;
