import React, { useEffect, useState } from "react";
import axios from "axios";
import styled from "@emotion/styled";
import ShopCard from "../../molecules/ShopMenu/ShopCard";

const ShopList = styled.ul`
	width: 100%;
	height: 100%;
	display: flex;
	flex-wrap: wrap;
	border-bottom: 1px solid #eee;
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
	shopId: number;
	image: string;
	name: string;
	discountedPrice: number;
	price: number;
	shopName: string;
};

const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

interface menuMatchType {
	menuMatchName?: string;
	menuSorting?: string;
	// eslint-disable-next-line react/require-default-props
	menuPaginationNumber: number;
}

const ShopCards: React.FC<menuMatchType> = ({
	menuMatchName,
	menuSorting,
	menuPaginationNumber,
}) => {
	const [shop, setShops] = useState<shopsDataType[]>([]);
	console.log("shop", shop);

	const SHOP_BOARD_API_BASE_URL = `http://54.180.134.20/api/products?category=${
		menuMatchName === "전체보기" ? "" : menuMatchName
	}&method=${menuSorting}&page=${menuPaginationNumber - 1}`;
	const BoardService = () => {
		return axios.get(SHOP_BOARD_API_BASE_URL);
	};
	useEffect(() => {
		console.log("menuMatchName: ", menuMatchName);
		console.log("menuSorting: ", menuSorting);
		console.log("menuPaginationNumber: ", menuPaginationNumber);

		BoardService().then(
			(res) => {
				setShops(res.data.data.products); // api가 연결 된 경우 -> back에서 데이터 불러옴
			},
			(error) => {
				console.error(error);
			}
		);
	}, [menuSorting, menuMatchName, menuPaginationNumber]);
	return (
		<ShopList>
			{shop.map((row) => (
				<ShopListLi key={row.shopId}>
					<ShopCard
						shopId={row.shopId}
						shopName={row.shopName}
						shopFoodImg={
							row.image
								? row.image
								: "http://cdn.onlinewebfonts.com/svg/img_305436.png"
						}
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
			<>{menuPaginationNumber}</>
		</ShopList>
	);
};

ShopCards.defaultProps = {
	menuMatchName: "",
	menuSorting: "",
	// eslint-disable-next-line react/default-props-match-prop-types
	menuPaginationNumber: 0,
};

export default ShopCards;
