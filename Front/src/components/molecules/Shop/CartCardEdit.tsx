import React, { useState } from "react";
// Styles
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import axios from "axios";
import ArrowRightAltRoundedIcon from "@mui/icons-material/ArrowRightAltRounded";
// redux
import ProductEditModal from "./ProductEditModal";
import { RootState } from "../../../app/store";
import { getAccessToken } from "../../../helpers/tokenControl";

type cardType = {
	id: number;
	shopFoodImg: string;
	shopName: string;
	shopFoodName: string;
	shopBeforeCost: number;
	shopFoodCost: number;
	shopMatchId: string;
	priorityUpFn(arrNum: number): void;
	priorityDownFn(arrNum: number): void;
	cardIndex: number;
};

const CartCardEdit: React.FC<cardType> = ({
	id,
	shopFoodImg,
	shopName,
	shopFoodCost,
	shopBeforeCost,
	shopFoodName,
	shopMatchId,
	priorityUpFn,
	priorityDownFn,
	cardIndex,
}) => {
	const [isModal, setIsModal] = useState(false);
	const handleModal = () => {
		setIsModal(!isModal);
	};
	const reduxPriorityStatus = useSelector((state: RootState) => {
		return state.updatePriority;
	});
	const daepyo = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		return axios.post(
			`http://54.180.134.20/api/manager/shops/${shopMatchId}/choice`,
			{
				productId: id,
			},
			config
		);
	};

	const daepyo2 = () => {
		daepyo().then(() => {
			console.log("설정");
		});
	};
	return (
		<CartCard key={id}>
			{reduxPriorityStatus && (
				<div className="arrow-btn">
					<button
						type="button"
						onClick={() => {
							priorityUpFn(cardIndex);
						}}
					>
						<i className="arrow up" />
					</button>
					<button
						type="button"
						onClick={() => {
							priorityDownFn(cardIndex);
						}}
					>
						<i className="arrow down" />
					</button>
					<button type="button" onClick={daepyo2}>
						대표상품
					</button>
				</div>
			)}
			<div className="card-img-ctn">
				<img src={shopFoodImg} alt="Food" />
			</div>
			<div className="cardInfo">
				<p>{shopName}</p>
				<p>{shopFoodName}</p>
			</div>
			<div className="right-wrapper">
				<div className="price-ctn">
					<p className="price">
						<s>({shopBeforeCost}원)</s>
						<ArrowRightAltRoundedIcon className="right-arrow" />
						<strong>{shopFoodCost}원</strong>
					</p>
				</div>
				<div className="btn-ctn">
					<button
						className="select-btn"
						type="button"
						onClick={handleModal}
					>
						수정
					</button>
				</div>
			</div>
			{!!isModal && (
				<ProductEditModal
					modal={handleModal}
					id={id}
					shopMatchId={shopMatchId}
					shopFoodName={shopFoodName}
					shopBeforeCost={shopBeforeCost}
					shopFoodCost={shopFoodCost}
				/>
			)}
		</CartCard>
	);
};

export default CartCardEdit;

const CartCard = styled.div`
	height: 181px;
	border: 1px solid #d3d3d3;
	padding: 10px 10px 10px 10px;
	margin: 25px 20px 20px 20px;
	display: flex;
	justify-content: space-evenly;
	align-items: center;
	border-radius: 8px;
	transition: 0.3s;
	:hover {
		box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
	}

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
		flex-direction: row;
		width: 250px;
		display: flex;
		align-items: center;
		justify-content: center;
		margin: 10px;

		.select-btn {
			width: 74px;
			height: 27px;
			border-radius: 8px;
			background-color: #cfcfcf;
			color: black;
			font-weight: 500;
			font-size: 13px;
			margin: 3px;
		}
	}

	.price {
		display: flex;
		align-items: center;
		justify-content: center;
		text-align: center;
	}

	.price > s {
		color: black;
	}

	.right-arrow {
		/* margin-top: 20px; */
		/* padding-top */
	}

	.arrow-btn {
		display: flex;
		flex-direction: column;
		align-items: center;
		transition: 0.3s;
		button {
			display: inline-flex;
			background-color: #fff;
		}
	}

	.arrow {
		border: solid black;
		border-width: 0 3px 3px 0;
		display: inline-block;
		padding: 4px;
	}

	.up {
		transform: rotate(-135deg);
		-webkit-transform: rotate(-135deg);
		margin-bottom: 20px;
	}

	.down {
		transform: rotate(45deg);
		-webkit-transform: rotate(45deg);
		margin-bottom: 10px;
	}
`;
