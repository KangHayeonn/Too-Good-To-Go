import React, { useState } from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";
import TimeSelectBtnAtom from "../../atoms/MenuButton/TimeSelectBtnAtom";

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

const ModalWrap = styled.div`
	width: 375px;
	height: 590px;
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
};

const OrderModal: React.FC<modal> = ({
	modal,
	shopTell,
	orderDetail,
	payment,
	createdTime,
}) => {
	const [selectTime, setSelectTime] = useState<string>();
	console.log(selectTime);
	const clickSelectTime = (numb: string) => {
		setSelectTime(numb);
		const btn = document.getElementsByClassName(
			"css-rwveo4"
		) as HTMLCollectionOf<HTMLElement>;
		if (numb === "20") {
			btn[0].style.backgroundColor = "#55b689";
			btn[0].style.color = "#fff";
			btn[1].style.backgroundColor = "#e0e0e0";
			btn[1].style.color = "#999";
			btn[2].style.backgroundColor = "#e0e0e0";
			btn[2].style.color = "#999";
			btn[3].style.backgroundColor = "#e0e0e0";
			btn[3].style.color = "#999";
			btn[4].style.backgroundColor = "#e0e0e0";
			btn[4].style.color = "#999";
			btn[5].style.backgroundColor = "#e0e0e0";
			btn[5].style.color = "#999";
		} else if (numb === "30") {
			btn[1].style.backgroundColor = "#55b689";
			btn[1].style.color = "#fff";
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
		} else if (numb === "40") {
			btn[2].style.backgroundColor = "#55b689";
			btn[2].style.color = "#fff";
			btn[0].style.backgroundColor = "#e0e0e0";
			btn[0].style.color = "#999";
			btn[1].style.backgroundColor = "#e0e0e0";
			btn[1].style.color = "#999";
			btn[3].style.backgroundColor = "#e0e0e0";
			btn[3].style.color = "#999";
			btn[4].style.backgroundColor = "#e0e0e0";
			btn[4].style.color = "#999";
			btn[5].style.backgroundColor = "#e0e0e0";
			btn[5].style.color = "#999";
		} else if (numb === "50") {
			btn[3].style.backgroundColor = "#55b689";
			btn[3].style.color = "#fff";
			btn[0].style.backgroundColor = "#e0e0e0";
			btn[0].style.color = "#999";
			btn[1].style.backgroundColor = "#e0e0e0";
			btn[1].style.color = "#999";
			btn[2].style.backgroundColor = "#e0e0e0";
			btn[2].style.color = "#999";
			btn[4].style.backgroundColor = "#e0e0e0";
			btn[4].style.color = "#999";
			btn[5].style.backgroundColor = "#e0e0e0";
			btn[5].style.color = "#999";
		} else if (numb === "60") {
			btn[4].style.backgroundColor = "#55b689";
			btn[4].style.color = "#fff";
			btn[0].style.backgroundColor = "#e0e0e0";
			btn[0].style.color = "#999";
			btn[1].style.backgroundColor = "#e0e0e0";
			btn[1].style.color = "#999";
			btn[3].style.backgroundColor = "#e0e0e0";
			btn[3].style.color = "#999";
			btn[2].style.backgroundColor = "#e0e0e0";
			btn[2].style.color = "#999";
			btn[5].style.backgroundColor = "#e0e0e0";
			btn[5].style.color = "#999";
		} else if (numb === "90") {
			btn[5].style.backgroundColor = "#55b689";
			btn[5].style.color = "#fff";
			btn[0].style.backgroundColor = "#e0e0e0";
			btn[0].style.color = "#999";
			btn[1].style.backgroundColor = "#e0e0e0";
			btn[1].style.color = "#999";
			btn[3].style.backgroundColor = "#e0e0e0";
			btn[3].style.color = "#999";
			btn[4].style.backgroundColor = "#e0e0e0";
			btn[4].style.color = "#999";
			btn[2].style.backgroundColor = "#e0e0e0";
			btn[2].style.color = "#999";
		}
	};

	const onchange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setSelectTime(target.value);
		const btn = document.getElementsByClassName(
			"css-rwveo4"
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
	return (
		<ModalMain aria-hidden>
			<ModalWrap onClick={(e) => e.stopPropagation()} aria-hidden>
				<ModalHead>
					<p>접수하기</p>
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
							<DetailText>{createdTime}</DetailText>
						</Detail>
						<Detail>
							<Text>결제수단</Text>
							<DetailText>{payment}</DetailText>
						</Detail>
						<Detail>
							<Text>요청사항</Text>
							<DetailText>{orderDetail}</DetailText>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>포장 예상 시간</BoldText>
						<TimeList>
							<TimeSelectBtnAtom
								text="20분"
								onClick={() => clickSelectTime("20")}
							/>
							<TimeSelectBtnAtom
								text="30분"
								onClick={() => clickSelectTime("30")}
							/>
							<TimeSelectBtnAtom
								text="40분"
								onClick={() => clickSelectTime("40")}
							/>
							<TimeSelectBtnAtom
								text="50분"
								onClick={() => clickSelectTime("50")}
							/>
							<TimeSelectBtnAtom
								text="60분"
								onClick={() => clickSelectTime("60")}
							/>
							<TimeSelectBtnAtom
								text="90분"
								onClick={() => clickSelectTime("90")}
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
							<Button>주문접수</Button>
						</ButtonWrap>
					</InfoBox>
					<RefuseWrap>
						<RefuseBtn>주문 거부</RefuseBtn>
					</RefuseWrap>
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default OrderModal;
