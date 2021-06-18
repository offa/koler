package com.chooloo.www.koler.ui.callactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chooloo.www.koler.databinding.CallActionsBinding
import com.chooloo.www.koler.ui.base.BaseFragment
import com.chooloo.www.koler.ui.base.BottomFragment
import com.chooloo.www.koler.ui.dialpad.DialpadFragment
import com.chooloo.www.koler.util.audio.AudioManager
import com.chooloo.www.koler.util.audio.AudioManager.AudioMode.IN_CALL
import com.chooloo.www.koler.util.callrecord.CallRecorder

class CallActionsFragment : BaseFragment(), CallActionsContract.View {
    private val _callRecorder by lazy { CallRecorder(baseActivity) }
    private val _binding by lazy { CallActionsBinding.inflate(layoutInflater) }
    private val _audioManager by lazy { AudioManager(baseActivity.applicationContext) }
    private val _presenter by lazy { CallActionsPresenter<CallActionsContract.View>(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = _binding.root

    override fun onSetup() {
        _audioManager.audioMode = IN_CALL

        _binding.apply {
            callActionHold.setOnClickListener { _presenter.onHoldClick() }
            callActionAddCall.setOnClickListener { _presenter.onAddCallClick() }
            callActionKeypad.setOnClickListener { _presenter.onKeypadClick() }
            callActionMute.setOnClickListener { _presenter.onMuteClick() }
            callActionRecord.setOnClickListener { _presenter.onRecordClick() }
            callActionSpeaker.setOnClickListener { _presenter.onSpeakerClick() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _callRecorder.stopRecording()
    }


    //region call actions view

    override fun addCall() {
        showError("Feature in development")
    }

    override fun openDialpad() {
        BottomFragment(DialpadFragment.newInstance(false).apply {
            setOnKeyDownListener(_presenter::onKeypadKey)
        }).show(childFragmentManager, DialpadFragment.TAG)
    }

    override fun stopRecording() {
        _callRecorder.stopRecording().also {
            showMessage("Finished recording at ${it?.filename}")
        }
    }

    override fun startRecording() {
        showError("Feature in development")
//        CallManager.sCall?.let {
//            _callRecorder.startRecording(
//                CallDetails.fromCall(it, _activity).contact.number ?: "Unknown"
//            )
//            blinkView(_binding.callActionRecord, 2000)
//        }
    }

    override fun toggleMute(isMute: Boolean) {
        _audioManager.isMuted = isMute
    }

    override fun toggleSpeaker(isSpeaker: Boolean) {
        _audioManager.isSpeakerOn = isSpeaker
    }

    //endregion


    companion object {
        fun newInstance() = CallActionsFragment()
    }
}