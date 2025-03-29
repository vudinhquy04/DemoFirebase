package com.quyvd.demofirebase

class CarModel {
    private var _id: String? = null
    var ten: String

    var namSX: Int

    var hang: String

    var gia: Double

    constructor(_id: String?, ten: String, namSX: Int, hang: String, gia: Double) {
        this._id = _id
        this.ten = ten
        this.namSX = namSX
        this.hang = hang
        this.gia = gia
    }

    constructor(ten: String, namSX: Int, hang: String, gia: Double) {
        this.ten = ten
        this.namSX = namSX
        this.hang = hang
        this.gia = gia
    }

    fun get_id(): String? {
        return _id
    }

    fun set_id(_id: String?) {
        this._id = _id
    }
}
