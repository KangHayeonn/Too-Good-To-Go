import React, { useState } from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";

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
	height: 570px;
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
	padding-right: 10px;
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
	width: 100%;
`;

const ButtonWrap = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	margin: 10px 0;
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

const InputStyle = styled.input`
	outline-style: none;
	border: 1px solid #999;
	width: auto;
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
	margin-bottom: 10px;
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
	shopName: string;
	shopAddress: string;
	shopTel: string;
	shopCategory: string;
	shopOpen: string;
	shopClose: string;
};

const ShopEditModal: React.FC<modal> = ({
	modal,
	shopName,
	shopAddress,
	shopTel,
	shopCategory,
	shopOpen,
	shopClose,
}) => {
	const [changeName, setChangeName] = useState<string>(shopName);
	const shopNameChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeName(target.value);
	};
	const [changeAddress, setChangeAddress] = useState<string>(shopAddress);
	const shopAddressChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeAddress(target.value);
	};
	const [changeTel, setChangeTel] = useState<string>(shopTel);
	const shopTelChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeTel(target.value);
	};
	const [changeCategory, setChangeCategory] = useState<string>(shopCategory);
	const shopCategoryChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeCategory(target.value);
	};
	const [changeOpen, setChangeOpen] = useState<string>(shopOpen);
	const shopOpenChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeOpen(target.value);
	};
	const [changeClose, setChangeClose] = useState<string>(shopClose);
	const shopCloseChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeClose(target.value);
	};

	return (
		<ModalMain aria-hidden>
			<ModalWrap onClick={(e) => e.stopPropagation()} aria-hidden>
				<ModalHead>
					<p>가게 정보 수정</p>
					<CloseIcon onClick={modal} />
				</ModalHead>
				<ModalInner>
					<InfoBox>
						<BoldText>가게 이름 수정</BoldText>
						<Detail>
							<Text>가게 이름</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopNameChange}
								placeholder="가게 이름"
								defaultValue={changeName}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 상세 정보 수정</BoldText>
						<Detail>
							<Text>가게 주소</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopAddressChange}
								placeholder="가게 주소"
								defaultValue={changeAddress}
							/>
						</Detail>
						<Detail>
							<Text>가게 전화번호</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopTelChange}
								placeholder="가게 전화번호"
								defaultValue={changeTel}
							/>
						</Detail>
						<Detail>
							<Text>가게 카테고리</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopCategoryChange}
								placeholder="가게 카테고리"
								defaultValue={changeCategory}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 영업시간 수정</BoldText>
						<Detail>
							<Text>영업 시작</Text>
							<InputStyle
								name="productName"
								type="time"
								onChange={shopOpenChange}
								placeholder="상품 가격"
								value={changeOpen}
							/>
						</Detail>
						<Detail>
							<Text>영업 종료</Text>
							<InputStyle
								name="productName"
								type="time"
								onChange={shopCloseChange}
								placeholder="상품 할인 가격"
								value={changeClose}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 이미지 수정</BoldText>
						<Detail>
							<InputStyle
								name="productName"
								type="file"
								accept="image/*"
								placeholder="상품 이름"
							/>
						</Detail>
					</InfoBox>
					<ButtonWrap>
						<Button>수정</Button>
					</ButtonWrap>
					<RefuseWrap>
						<RefuseBtn>상점 삭제</RefuseBtn>
					</RefuseWrap>
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default ShopEditModal;
