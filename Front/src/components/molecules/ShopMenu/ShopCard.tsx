import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

const ShopCardStyle = styled.div`
	border-bottom-left-radius: 1px;
	border-bottom-right-radius: 1px;
	box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.3);
	height: 100%;
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
	margin: 15px 15px;
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
	font-size: 12px;
	font-weight: 500;
`;

const shopFoodNameStyle = css`
	color: #333;
	font-size: 19px;
	font-weight: 700;
	overflow: hidden;
	width: 100%;
	text-align: left;
	text-overflow: ellipsis;
	white-space: nowrap;
`;

const saleValueStyle = css`
	font-size: 15px;
	color: #54b689;
	font-weight: 600;
`;

const costValueStyle = css`
	font-size: 16px;
	color: #333;
	font-weight: 600;
`;

const beforeSaleValueStyle = css`
	text-decoration: line-through;
	color: #999;
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
			<Link to="/shop" css={ShopCover}>
				<ShopImg src={shopFoodImg} alt="음식점 사진" />
			</Link>
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
