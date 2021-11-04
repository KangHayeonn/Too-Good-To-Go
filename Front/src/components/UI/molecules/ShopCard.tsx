import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";

const ShopCardStyle = styled.div`
	border: 1px solid black;
	border-bottom-left-radius: 20px;
	border-bottom-right-radius: 20px;
`;

const ShopImg = styled.img`
	width: 100%;
	height: 222px;
	object-fit: cover;
	transition: all 0.3s ease-in-out;
	transform: scale(1);
	&: hover {
		transform: scale(1.02);
		transition: all 0.3s ease-in-out;
	}
`;

const ShopCover = css`
	height: 222px;
	overflow: hidden;
	display: block;
	cursor: pointer;
`;

const ShopCardInfo = styled.div`
	margin: 10px 10px;
`;

const ShopCardInfoHead = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-start;
`;

const ShopCardInfoCenter = styled.div`
	display: flex;
	align-items: flex-start;

	p {
		margin-right: 5px;
	}
`;

const shopNameStyle = css`
	color: #333;
	font-size: 13px;
	font-weight: 400;
`;

const shopFoodNameStyle = css`
	color: #333;
	font-size: 20px;
	font-weight: 500;
`;

const saleValueStyle = css`
	font-size: 15px;
	color: #54b689;
	font-weight: 500;
`;

const costValueStyle = css`
	font-size: 16px;
	color: #333;
	font-weight: 600;
`;

const beforeSaleValueStyle = css`
	text-decoration: line-through;
`;

type shopCardType = {
	shopName: string;
	shopFoodImg: string;
	shopFoodName: string;
	shopFoodSale: number;
	shopFoodCost: number;
	shopBeforeCost: number;
};

const ShopCard: React.FC<shopCardType> = ({
	shopName,
	shopFoodImg,
	shopFoodName,
	shopFoodSale,
	shopFoodCost,
	shopBeforeCost,
}) => {
	return (
		<ShopCardStyle>
			<a href="#!" css={ShopCover}>
				<ShopImg src={shopFoodImg} alt="음식점 사진" />
			</a>
			<ShopCardInfo>
				<ShopCardInfoHead>
					<p css={shopNameStyle}>{shopName}</p>
					<p css={shopFoodNameStyle}>{shopFoodName}</p>
				</ShopCardInfoHead>
				<ShopCardInfoCenter>
					<p css={saleValueStyle}>{shopFoodSale}%</p>
					<p css={costValueStyle}>{shopFoodCost}원</p>
					<p css={beforeSaleValueStyle}>{shopBeforeCost}원</p>
				</ShopCardInfoCenter>
			</ShopCardInfo>
		</ShopCardStyle>
	);
};

export default ShopCard;
