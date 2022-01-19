import React, { useState, useEffect } from "react";
import axios from "axios";
import styled from "@emotion/styled";
import ShopCard from "../../molecules/ShopMenu/ShopCard";
import { ShopData2 } from "../../molecules/ShopDummyData";

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
	shop: { id: number; name: string; image: string; category: Array<string> };
	data?: Array<string | number>;
};

const SHOP_BOARD_API_BASE_URL = "http://localhost:8080/api/shopboardsa";
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
		BoardService().then(
			(res) => {
				setShops(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
			},
			() => {
				setShops(ShopData2); // api가 연결되지 않은 경우 -> 위의 예시 데이터 출력
			}
		);
	}, []);
	return (
		<ShopList>
			{shop.slice(start, last).map((row) => (
				<ShopListLi key={row.id}>
					<ShopCard
						shopId={row.shop.id}
						shopName={row.shop.name}
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
