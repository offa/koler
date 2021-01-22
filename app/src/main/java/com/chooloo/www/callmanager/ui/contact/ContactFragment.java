package com.chooloo.www.callmanager.ui.contact;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chooloo.www.callmanager.databinding.FragmentContactBinding;
import com.chooloo.www.callmanager.entity.Contact;
import com.chooloo.www.callmanager.ui.base.BaseFragment;
import com.chooloo.www.callmanager.util.ContactUtils;

import static android.Manifest.permission.WRITE_CONTACTS;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.chooloo.www.callmanager.util.AnimationUtils.animateViews;

public class ContactFragment extends BaseFragment implements ContactMvpView {

    public static final String CONTACT_ARG = "contact";

    private ContactPresenter<ContactMvpView> mPresenter;
    private Contact mContact;
    private FragmentContactBinding binding;


    public static ContactFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ARG, contact);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void onSetup() {
        mPresenter = new ContactPresenter<>();
        mPresenter.onAttach(this);

        // contact details
        mContact = (Contact) getArgsSafely().getSerializable(CONTACT_ARG);
        binding.contactTextName.setText(mContact.getName());
        binding.contactTextNumber.setText(mContact.getNumber());
        binding.contactFavoriteIcon.setVisibility(mContact.getStarred() ? VISIBLE : GONE);
        if (mContact.getPhotoUri() != null) {
            binding.contactImage.setImageURI(Uri.parse(mContact.getPhotoUri()));
        }

        // click listeners
        binding.contactButtonSms.setOnClickListener(view -> mPresenter.onActionSms());
        binding.contactButtonCall.setOnClickListener(view -> mPresenter.onActionCall());
        binding.contactButtonEdit.setOnClickListener(view -> mPresenter.onActionEdit());
        binding.contactButtonDelete.setOnClickListener(view -> mPresenter.onActionDelete());

        animateLayout();
    }

    @Override
    public void callContact() {
//        CallManager.call(mActivity, mContact.getNumber());
    }

    @Override
    public void sendSmsToContact() {
//        Utilities.openSmsWithNumber(mActivity, mContact.getNumber());
    }

    @Override
    public void editContact() {
        ContactUtils.openContactToEdit(mActivity, mContact);
    }

    @Override
    public void openContact() {
        ContactUtils.openContact(mActivity, mContact);
    }

    @Override
    public void deleteContact() {
        if (hasPermission(WRITE_CONTACTS)) {
            ContactUtils.deleteContact(mActivity, mContact);
            Toast.makeText(mActivity, "Contact deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mActivity, "I dont have the permission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void animateLayout() {
        animateViews(new View[]{
                binding.contactImage,
                binding.contactTextName,
//                binding.contactActionsLayout
        }, 130, true);
    }
}
