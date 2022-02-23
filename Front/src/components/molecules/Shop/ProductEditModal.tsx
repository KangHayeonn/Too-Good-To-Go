import React, { useState } from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";
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

const ModalWrap = styled.div`
	width: 375px;
	height: 440px;
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
	shopFoodName: string;
	shopBeforeCost: number;
	shopFoodCost: number;
	shopMatchId: string;
	id: number;
};

type productType = {
	name: string;
	price: number;
	discountedPrice: number;
};

const OrderModal: React.FC<modal> = ({
	modal,
	shopFoodName,
	shopBeforeCost,
	shopFoodCost,
	shopMatchId,
	id,
}) => {
	const [changeFoodName, setChangeFoodName] = useState<string>(shopFoodName);
	const [changeFoodBeforeCost, setChangeFoodBeforeCost] =
		useState<number>(shopBeforeCost);
	const [changeFoodCost, setChangeFoodCost] = useState<number>(shopFoodCost);

	const [productInfo, setProductInfo] = useState<productType>({
		name: changeFoodName,
		price: changeFoodBeforeCost,
		discountedPrice: changeFoodCost,
	});

	const [image, setImage] = useState<File>();

	const formData = new FormData();

	const foodNameChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeFoodName(target.value);
		setProductInfo({
			...productInfo,
			name: target.value,
		});
	};
	const foodBeforeCostChange = (
		e: React.FormEvent<HTMLInputElement>
	): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeFoodBeforeCost(+target.value);
		setProductInfo({
			...productInfo,
			price: +target.value,
		});
	};
	const foodCostChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeFoodCost(+target.value);
		setProductInfo({
			...productInfo,
			discountedPrice: +target.value,
		});
	};
	const onSaveFiles = (e: React.ChangeEvent<HTMLInputElement>) => {
		if (e.target.files) {
			const file: FileList = e.target.files;
			const uploadFile = file[0];
			setImage(uploadFile);
		}
	};

	const appendFormData = () => {
		console.log(image);
		const dtoObj = new Blob([JSON.stringify(productInfo)], {
			type: "application/json",
		});
		formData.set("request", dtoObj);

		if (image) {
			formData.append("file", image);
		} else {
			const imageJson = new File([], "");
			formData.set("file", imageJson);
		}

		console.log(productInfo);
		console.log(...formData);
	};

	const EditPost = () => {
		appendFormData();

		PostProductInfo().then(
			() => {
				console.log("post success");
				console.log(productInfo);
			},
			(err) => {
				console.log("post fail");
				console.log(err);
			}
		);
	};
	const PRODUCT_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}/products/${id}`;
	const PostProductInfo = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
				"Content-Type": "multipart/form-data",
			},
		};
		return axios.post(PRODUCT_API_URL, formData, config);
	};
	const PRODUCT_DELETE_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}/products/${id}`;
	const DeleteProduct = () => {
		return axios({
			method: "delete",
			url: `${PRODUCT_DELETE_API_URL}`,
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		});
	};
	const DeletePost = () => {
		// eslint-disable-next-line no-restricted-globals
		const res = confirm("정말로 삭제하시겠습니까?");
		console.log(image);
		if (res) {
			DeleteProduct().then(
				() => {
					console.log("삭제완료");
				},
				() => {
					console.log("삭제실패");
				}
			);
		}
	};
	return (
		<ModalMain aria-hidden>
			<ModalWrap onClick={(e) => e.stopPropagation()} aria-hidden>
				<ModalHead>
					<p>상품정보 수정</p>
					<CloseIcon onClick={modal} />
				</ModalHead>
				<ModalInner>
					<InfoBox>
						<BoldText>상품 이름</BoldText>
						<Detail>
							<Text>상품 이름</Text>
							<InputStyle
								name="name"
								type="text"
								onChange={foodNameChange}
								placeholder="상품 이름"
								defaultValue={changeFoodName}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>상품 가격</BoldText>
						<Detail>
							<Text>상품 가격</Text>
							<InputStyle
								name="price"
								type="number"
								onChange={foodBeforeCostChange}
								placeholder="상품 가격"
								defaultValue={changeFoodBeforeCost}
							/>
						</Detail>
						<Detail>
							<Text>상품 할인 가격</Text>
							<InputStyle
								name="discountedPrice"
								type="number"
								onChange={foodCostChange}
								placeholder="상품 할인 가격"
								defaultValue={changeFoodCost}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>상품 이미지</BoldText>
						<Detail>
							<InputStyle
								name="image"
								type="file"
								accept="image/*"
								placeholder="상품 이미지"
								onChange={onSaveFiles}
							/>
						</Detail>
					</InfoBox>
					<ButtonWrap>
						<Button onClick={EditPost}>수정</Button>
					</ButtonWrap>
					<RefuseWrap>
						<RefuseBtn onClick={DeletePost}>상품 삭제</RefuseBtn>
					</RefuseWrap>
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default OrderModal;
