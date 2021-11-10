import React from "react";
import styled from "@emotion/styled";
import { shopData } from "../ShopDummyData";

interface Ishops {
	shopId: number;
	shopFoodImg: string;
	shopName: string;
	shopFoodName: string;
	shopFoodSale: number;
	shopFoodCost: number;
	shopBeforeCost: number;
}

const CartCards = () => {
	return (
		<Wrapper>
			{shopData.map((data: Ishops) => {
				return (
					<CartCard key={data.shopId}>
						<div className="card-img-ctn">
							<img src={data.shopFoodImg} alt="Food" />
						</div>
						<div className="cardInfo">
							<p>{data.shopName}</p>
							<strong>shopType, Pipe, foodType</strong>
							<p>{data.shopFoodName}</p>
						</div>
						<div className="right-wrapper">
							<div className="price-ctn">
								<p className="price">{data.shopFoodCost}원</p>
							</div>
							<div className="btn-ctn">
								<button type="button">수정</button>
								<button type="button">선택</button>
							</div>
						</div>
					</CartCard>
				);
			})}
		</Wrapper>
	);
};

export default CartCards;

const Wrapper = styled.div`
	width: 680px;
	height: 579px;
	/* border: 1px solid blue; */
	border-top: 1px solid #d3d3d3;
	overflow: scroll;
	padding: 30px 10px;
	margin-right: 25px;
`;

const CartCard = styled.div`
	width: 640px;
	height: 181px;
	border: 1px solid #d3d3d3;
	padding: 10px 10px 10px 0;
	margin: 25px 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;

	.card-img-ctn {
		width: 134px;
	}

	img {
		width: 100%;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 12px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 20px;
	}

	.cardInfo > * {
		margin: 6px;
		margin-left: 15px;
	}

	p:last-child {
		font-weight: 600;
	}

	.cardInfo > strong {
		color: #85bf70;
	}

	.right-wrapper {
		margin-left: 160px;
	}

	.btn-ctn {
		/* display: border-box; */
		flex-direction: row;
		width: 200px;
	}

	Button {
		width: 74px;
		height: 27px;
		border-radius: 8px;
		background-color: #cfcfcf;
		color: black;
		font-weight: 500;
		font-size: 13px;
	}
`;
