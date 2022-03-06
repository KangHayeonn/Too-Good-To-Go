import React, { useCallback } from "react";
import { useHistory } from "react-router-dom";
import { useSelector } from "react-redux";
import axios from "axios";
import styled from "@emotion/styled";
import { RootState } from "../../../app/store";
import { getAccessToken } from "../../../helpers/tokenControl";

const URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)

type ModalProps = {
	setModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

const OrderedModal: React.FC<ModalProps> = ({ setModalOpen }) => {
	const history = useHistory();
	const orderInfo = useSelector((state: RootState) => {
		return state.orderInfo;
	});

	const onClickOrdered = () => {
		axios
			.post(
				`${URL}/orders`,
				{
					phone: orderInfo.phone,
					products: orderInfo.products,
					requirement: orderInfo.requirement,
					paymentMethod: orderInfo.paymentMethod,
					needDisposables: orderInfo.plasticUse,
					cacheRequirement: orderInfo.cacheRequirement,
					cachePaymentMethod: orderInfo.cachePaymentMethod,
				},
				{
					headers: { Authorization: `Bearer ${getAccessToken()}` },
				}
			)
			.then((res) => {
				console.log(res, " 주문 완료 성공");
				// eslint-disable-next-line no-alert
				alert("주문완료 되었습니다.");
				history.push("/");
				// erase cart redux,
			})
			.catch((e) => {
				console.log(e);
				console.log("주문 완료 실패");
			});
	};

	const onClickClosed = useCallback(() => {
		setModalOpen(false);
	}, []);

	return (
		<>
			<Container>
				<Popup>
					<Header>
						<span className="head-title">결제수단선택</span>
					</Header>
					<Body>
						<div className="body-contentbox">
							<MenuBox>주문하시겠습니까?</MenuBox>
						</div>
					</Body>
					<Footer>
						<button
							type="button"
							className="pop-btn confirm"
							id="confirm"
							onClick={onClickOrdered}
						>
							확인
						</button>
						<button
							type="button"
							className="pop-btn close"
							id="close"
							onClick={onClickClosed}
						>
							닫기
						</button>
					</Footer>
				</Popup>
			</Container>
		</>
	);
};

const Container = styled.div`
	background: rgba(0, 0, 0, 0.3);
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 15px;
	z-index: 2;
`;
const Popup = styled.div`
	width: 100%;
	max-width: 350px;
	border-radius: 10px;
	overflow: hidden;
	background: #6ec19b;
	box-shadow: 5px 10px 10px 1px rgba(0, 0, 0, 0.3);
`;
const Header = styled.div`
	width: 100%;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 19px;
	font-weight: bold;
	color: #404040;
`;
const Body = styled.div`
	width: 100%;
	height: 50px;
	background: #ffffff;
	display: flex;
	justify-content: center;
	padding-top: 30px;
`;
const Footer = styled.div`
	width: 100%;
	display: inline-flex;
	height: 40px;
	justify-content: center;
	align-tems: center;
	float: left;
	color: #ffffff;
	cursor: pointer;

	.pop-btn {
		width: 50%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.pop-btn.confirm {
		border-right: 1px solid #d6e3dd; //오른쪽 줄
	}
`;
const MenuBox = styled.ul`
	width: 100%;
	height: 100%;
	min-height: 100px; //최소 높이
	max-height: 200px; //최대 높이
`;

export default OrderedModal;
