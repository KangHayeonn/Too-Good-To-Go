import React, { useState } from "react";
import styled from "@emotion/styled";
import axios from "axios";
import moment from "moment";
import CloseIcon from "@mui/icons-material/Close";
import TimeSelectBtnAtom from "../../atoms/MenuButton/TimeSelectBtnAtom";
import { getAccessToken } from "../../../helpers/tokenControl";

const ModalMain = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #00000080;
	z-index: 10000;
`;

interface theme {
	status: string;
}

const ModalWrap = styled.div`
	width: 375px;
	height: ${(props: theme) =>
		props.status === "접수대기" ? "607px" : "328px"};
	background-color: #fff;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
`;

const ModalInner = styled.div`
	margin: 20px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
`;

const BoldText = styled.p`
	font-size: 18px;
	font-weight: 600;
	margin-bottom: 10px;
	margin-top: 10px;
`;

const Text = styled.p`
	font-size: 15px;
	font-weight: 400;
	color: #999;
	margin-right: 10px;
`;

const Button = styled.div`
	width: 30%;
	height: 40px;
	background-color: #55b689;
	color: #fff;
	cursor: pointer;

	display: flex;
	justify-content: center;
	align-items: center;
`;

const Detail = styled.div`
	display: flex;
	align-items: center;
	margin-bottom: 5px;
`;

const DetailText = styled.p`
	font-size: 15px;
	font-weight: 400;
	color: #000;
`;

const ButtonWrap = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
`;

const ModalHead = styled.div`
	width: 100%;
	height: 50px;
	background-color: #363636;
	color: #fff;
	font-weight: 500;
	display: flex;
	align-items: center;
	justify-content: space-between;
	& > p {
		margin-left: 20px;
	}

	& > svg {
		margin-right: 20px;
		cursor: pointer;
	}
`;

const TimeList = styled.ul`
	width: 100%;
	height: 100%;
	display: flex;
	flex-wrap: wrap;
	margin-top: 1px;
	margin-bottom: 10px;
`;

const TimeInput = styled.input`
	width: 70%;
	outline-style: none;
	border: 1px solid #999;
	border-right: 0;
`;

const RefuseWrap = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	width: 100%;
	margin-top: 10px;
`;

const RefuseBtn = styled.p`
	color: #ff0000;
	font-size: 13px;
	font-weight: 600;
	cursor: pointer;
`;

const InfoBox = styled.div`
	width: 100%;
	height: auto;
	display: flex;
	padding-bottom: 10px;
	flex-direction: column;
	align-items: flex-start;
`;

type modal = {
	modal: () => void;
	shopTell: string;
	orderDetail: string;
	payment: string;
	createdTime: string;
	orderId: number;
	status: string;
	needDisposables: boolean;
	etaLoad: string;
};

type etaType = {
	eta: string;
};

const OrderModal: React.FC<modal> = ({
	modal,
	shopTell,
	orderDetail,
	payment,
	createdTime,
	orderId,
	status,
	needDisposables,
	etaLoad,
}) => {
	const [selectTime, setSelectTime] = useState<number>(0);
	const [eta, setEta] = useState<etaType>({ eta: "" });
	const [timeBtnColor, setTimeBtnColor] = useState<Array<string>>([
		"#e0e0e0",
		"#e0e0e0",
		"#e0e0e0",
		"#e0e0e0",
		"#e0e0e0",
		"#e0e0e0",
	]);
	const [timeTextColor, setTimeTextColor] = useState<Array<string>>([
		"#999",
		"#999",
		"#999",
		"#999",
		"#999",
		"#999",
	]);

	const postOrderUrl = `http://54.180.134.20/api/manager/orders/${orderId}:accept`;
	const OrderAccept = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		console.log(eta);
		return axios.post(postOrderUrl, eta, config);
	};

	const post = () => {
		OrderAccept().then(
			() => {
				console.log("성공");
			},
			() => {
				console.log("실패");
			}
		);
	};

	const clickSelectTime = (numb: string) => {
		setSelectTime(+numb);
		const orderCheckTime = new Date(createdTime);
		orderCheckTime.setMinutes(orderCheckTime.getMinutes() + selectTime);
		const momentTime = moment(orderCheckTime).toISOString().toString();
		setEta({ eta: momentTime });
		console.log(momentTime);
		// eslint-disable-next-line prefer-const
		let newArr = [...timeBtnColor];
		// eslint-disable-next-line prefer-const
		let newArr2 = [...timeTextColor];

		if (numb === "20") {
			newArr[0] = "#55b689";
			newArr[1] = "#e0e0e0";
			newArr[2] = "#e0e0e0";
			newArr[3] = "#e0e0e0";
			newArr[4] = "#e0e0e0";
			newArr[5] = "#e0e0e0";
			setTimeBtnColor(newArr);

			newArr2[0] = "#fff";
			newArr2[1] = "#999";
			newArr2[2] = "#999";
			newArr2[3] = "#999";
			newArr2[4] = "#999";
			newArr2[5] = "#999";
			setTimeTextColor(newArr2);
		} else if (numb === "30") {
			newArr[1] = "#55b689";
			newArr[0] = "#e0e0e0";
			newArr[2] = "#e0e0e0";
			newArr[3] = "#e0e0e0";
			newArr[4] = "#e0e0e0";
			newArr[5] = "#e0e0e0";
			setTimeBtnColor(newArr);
			newArr2[1] = "#fff";
			newArr2[0] = "#999";
			newArr2[2] = "#999";
			newArr2[3] = "#999";
			newArr2[4] = "#999";
			newArr2[5] = "#999";
			setTimeTextColor(newArr2);
		} else if (numb === "40") {
			newArr[2] = "#55b689";
			newArr[0] = "#e0e0e0";
			newArr[1] = "#e0e0e0";
			newArr[3] = "#e0e0e0";
			newArr[4] = "#e0e0e0";
			newArr[5] = "#e0e0e0";
			setTimeBtnColor(newArr);
			newArr2[2] = "#fff";
			newArr2[1] = "#999";
			newArr2[0] = "#999";
			newArr2[3] = "#999";
			newArr2[4] = "#999";
			newArr2[5] = "#999";
			setTimeTextColor(newArr2);
		} else if (numb === "50") {
			newArr[3] = "#55b689";
			newArr[0] = "#e0e0e0";
			newArr[1] = "#e0e0e0";
			newArr[2] = "#e0e0e0";
			newArr[4] = "#e0e0e0";
			newArr[5] = "#e0e0e0";
			setTimeBtnColor(newArr);
			newArr2[3] = "#fff";
			newArr2[1] = "#999";
			newArr2[2] = "#999";
			newArr2[0] = "#999";
			newArr2[4] = "#999";
			newArr2[5] = "#999";
			setTimeTextColor(newArr2);
		} else if (numb === "60") {
			newArr[4] = "#55b689";
			newArr[0] = "#e0e0e0";
			newArr[1] = "#e0e0e0";
			newArr[3] = "#e0e0e0";
			newArr[2] = "#e0e0e0";
			newArr[5] = "#e0e0e0";
			setTimeBtnColor(newArr);
			newArr2[4] = "#fff";
			newArr2[1] = "#999";
			newArr2[2] = "#999";
			newArr2[3] = "#999";
			newArr2[0] = "#999";
			newArr2[5] = "#999";
			setTimeTextColor(newArr2);
		} else if (numb === "90") {
			newArr[5] = "#55b689";
			newArr[0] = "#e0e0e0";
			newArr[1] = "#e0e0e0";
			newArr[3] = "#e0e0e0";
			newArr[4] = "#e0e0e0";
			newArr[2] = "#e0e0e0";
			setTimeBtnColor(newArr);
			newArr2[5] = "#fff";
			newArr2[1] = "#999";
			newArr2[2] = "#999";
			newArr2[3] = "#999";
			newArr2[4] = "#999";
			newArr2[0] = "#999";
			setTimeTextColor(newArr2);
		}
	};

	const onchange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setSelectTime(+target.value);
		const orderCheckTime = new Date(createdTime);
		orderCheckTime.setMinutes(orderCheckTime.getMinutes() + selectTime);
		const momentTime = moment(orderCheckTime).toISOString().toString();
		setEta({ eta: momentTime });
		console.log(momentTime);
		const btn = document.getElementsByClassName(
			"timeSelectBtn"
		) as HTMLCollectionOf<HTMLElement>;

		btn[1].style.backgroundColor = "#e0e0e0";
		btn[1].style.color = "#999";
		btn[0].style.backgroundColor = "#e0e0e0";
		btn[0].style.color = "#999";
		btn[2].style.backgroundColor = "#e0e0e0";
		btn[2].style.color = "#999";
		btn[3].style.backgroundColor = "#e0e0e0";
		btn[3].style.color = "#999";
		btn[4].style.backgroundColor = "#e0e0e0";
		btn[4].style.color = "#999";
		btn[5].style.backgroundColor = "#e0e0e0";
		btn[5].style.color = "#999";
	};
	const deleteUrl = `http://54.180.134.20/api/orders/${orderId}`;
	const DenyOrder = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		return axios.delete(deleteUrl, config);
	};

	const denyPost = () => {
		DenyOrder().then(() => {
			console.log("success delete");
		});
	};

	const time = moment(createdTime).format("YYYY년 MM월 DD일 hh시 mm분 ss초");
	const CompleteTime = moment(etaLoad).format(
		"YYYY년 MM월 DD일 hh시 mm분 ss초"
	);
	return (
		<ModalMain aria-hidden>
			<ModalWrap
				status={status}
				onClick={(e) => e.stopPropagation()}
				aria-hidden
			>
				<ModalHead>
					<p>
						{status === "접수대기"
							? "주문 접수하기"
							: "주문 상세보기"}
					</p>
					<CloseIcon onClick={modal} />
				</ModalHead>
				<ModalInner>
					<InfoBox>
						<BoldText>주문자 정보</BoldText>
						<Detail>
							<Text>전화번호</Text>
							<DetailText>{shopTell}</DetailText>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>주문 상세정보</BoldText>
						<Detail>
							<Text>주문시각</Text>
							<DetailText>{time}</DetailText>
						</Detail>
						{status === "완료" ? (
							<Detail>
								<Text>주문 완료 시각</Text>
								<DetailText>{CompleteTime}</DetailText>
							</Detail>
						) : null}
						<Detail>
							<Text>결제수단</Text>
							<DetailText>{payment}</DetailText>
						</Detail>
						<Detail>
							<Text>요청사항</Text>
							<DetailText>{orderDetail}</DetailText>
						</Detail>
						<Detail>
							<Text>일회용품 필요 여부</Text>
							<DetailText>
								{needDisposables ? "O" : "X"}
							</DetailText>
						</Detail>
					</InfoBox>
					{status === "접수대기" ? (
						<>
							<InfoBox>
								<BoldText>포장 예상 시간</BoldText>
								<TimeList>
									<TimeSelectBtnAtom
										text="20분"
										onClick={() => clickSelectTime("20")}
										background={timeBtnColor[0]}
										textColor={timeTextColor[0]}
									/>
									<TimeSelectBtnAtom
										text="30분"
										onClick={() => clickSelectTime("30")}
										background={timeBtnColor[1]}
										textColor={timeTextColor[1]}
									/>
									<TimeSelectBtnAtom
										text="40분"
										onClick={() => clickSelectTime("40")}
										background={timeBtnColor[2]}
										textColor={timeTextColor[2]}
									/>
									<TimeSelectBtnAtom
										text="50분"
										onClick={() => clickSelectTime("50")}
										background={timeBtnColor[3]}
										textColor={timeTextColor[3]}
									/>
									<TimeSelectBtnAtom
										text="60분"
										onClick={() => clickSelectTime("60")}
										background={timeBtnColor[4]}
										textColor={timeTextColor[4]}
									/>
									<TimeSelectBtnAtom
										text="90분"
										onClick={() => clickSelectTime("90")}
										background={timeBtnColor[5]}
										textColor={timeTextColor[5]}
									/>
								</TimeList>
								<ButtonWrap>
									<TimeInput
										name="time"
										type="number"
										placeholder="조리 완성 예상 시간(분)을 입력해주세요."
										onChange={onchange}
										value={selectTime}
									/>
									<Button onClick={post}>주문접수</Button>
								</ButtonWrap>
							</InfoBox>
							<RefuseWrap>
								<RefuseBtn onClick={denyPost}>
									주문 거부
								</RefuseBtn>
							</RefuseWrap>
						</>
					) : null}
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default OrderModal;
