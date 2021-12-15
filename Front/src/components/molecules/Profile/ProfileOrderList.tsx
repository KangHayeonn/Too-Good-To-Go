import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { Link } from "react-router-dom";
import { shopData } from "../ShopDummyData";
import api from "./api/posts";

const ProfileOrderList: React.FC = () => {
	// Axios data cannot be added in order list.
	// product's image attribute missing.
	const initialState: any[] | (() => any[]) = [];
	const [orderData, setOrderData] = useState(initialState);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await api.get("");
				setOrderData(response.data);
			} catch (err) {
				// Not in the 200 response range
			}
		};

		fetchData();
	}, []);
	console.log(orderData);

	return (
		<>
			<EditTitle className="edit-title">주문목록</EditTitle>
			<OrderListContainer>
				{shopData.map((card) => {
					return (
						<ProfileCard key={card.shopId}>
							<div className="card-img-ctn">
								<img src={card.shopFoodImg} alt="Food" />
							</div>
							<div className="cardInfo">
								<div className="cardInfo-flex">
									<strong>픽업 완료</strong>
									<p> - today&apos;s date</p>
									<p className="food-cost">
										{card.shopFoodCost}원
									</p>
								</div>
								<p className="card-info-text">
									<strong>{card.shopName}</strong>
									<span className="grey-text">|</span>
									{card.shopName}
								</p>
								<p>{card.shopFoodName}</p>
							</div>
							<div className="button-wrapper">
								<Link to="/"> 주문 정보 </Link>
								<Link to="/"> 가게 정보 </Link>
								<Link to="/"> 리뷰 쓰기 </Link>
							</div>
						</ProfileCard>
					);
				})}
			</OrderListContainer>
		</>
	);
};

export default ProfileOrderList;

const EditTitle = styled.div`
	position: relative;
	width: 120px;
	top: 26px;
	left: 37px;
	font-size: 20px;
	/* border: 1px solid purple; */
`;

const OrderListContainer = styled.div`
	/* border: 1px dotted red; */
	/* width: 800px; */
	min-height: 700px;
	max-height: auto;

	/* margin-left: 36px; */
	margin-top: 80px;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-left: 18px;
`;

const ProfileCard = styled.div`
	width: 626px;
	height: 161px;
	border: 1px solid #d3d3d3;
	/* padding: 0px 10px 0px 0; */
	overflow: hidden;
	margin: 25px 25px 0px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;
	justify-content: space-between;

	transition: 0.3s;

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
		width: 300px;
		position: relative;
		right: 25px;

		.cardInfo-flex {
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: flex-start;
			width: 100%;

			.food-cost {
				position: relative;
				left: 90px;
				font-size: 13px;
				color: #736e6e;
				width: 50px;
			}
		}
	}

	.cardInfo > * {
		margin: 6px;
		margin-left: 15px;
	}

	p:last-child {
		font-weight: 600;
	}

	p > strong {
		color: #85bf70;
	}

	.card-info-text {
		font-size: 16px;
		.grey-text {
			margin: 5px;
			color: #c0baba;
		}
	}

	.button-wrapper {
		border-left: 1px solid #d6d6d6;
		height: 100%;
		width: 217px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		a {
			border: 1px solid #f0f0f0;
			color: #646464;
			width: 161px;
			height: 25px;
			padding-top: 2px;
			margin: 8px;
		}
	}
`;
